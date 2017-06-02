package pw.evan.PlayerFreeze.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.evan.PlayerFreeze.Main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class UserManager
{
    public class User
    {
        private UUID uuid;
        private String username;
        private boolean frozen = false;
        private long frozenUntil = -1;

        private User(UUID uuid, String username, boolean frozen, long frozenUntil){
            this.uuid = uuid;
            this.setUsername(username);
            this.setFrozen(frozen);
            this.setFrozenUntil(frozenUntil);
        }
        public User(UUID uuid, String username, long frozenUntil){
            this(uuid, username, true, frozenUntil);
        }
        public User(UUID uuid, String username, boolean frozen){
            this(uuid, username, frozen, -1);
        }
        public User(UUID uuid, String username){
            this(uuid, username, false, -1);
        }

        public UUID getUuid()
        {
            return uuid;
        }

        public String getUsername()
        {
            return username;
        }

        public boolean isFrozen()
        {
            return frozen;
        }

        public boolean isTempFrozen()
        {
            return getFrozenUntil() > 0;
        }

        public long getFrozenUntil()
        {
            return frozenUntil;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public void setFrozen(boolean frozen)
        {
            this.frozen = frozen;
            if(!frozen){
                setFrozenUntil(-1);
            }
        }

        public void setFrozenUntil(long frozenUntil)
        {
            this.frozenUntil = frozenUntil;
            if(frozenUntil > 0 ){
                this.frozen = true;
            }
            else{
                this.frozen = false;
            }
        }
    }

    private HashMap<UUID, User> users = new HashMap<>();

    private File getStorageDirectory(){
        File storageDirectory = new File(plugin.getDataFolder()+File.separator+"users");
        if(storageDirectory.exists()){
            if(storageDirectory.isDirectory()){
                return storageDirectory;
            }
            else
            {
                throw new IllegalStateException(storageDirectory.getAbsolutePath()+" already exists and is not a directory!");
            }
        }
        else
        {
            if(storageDirectory.mkdir())
            {
                return storageDirectory;
            }
            else
            {
                throw new RuntimeException("Creating storage directory "+storageDirectory.getAbsolutePath()+" failed!");
            }
        }
    }

    private FileConfiguration getPlayerStorage(UUID uuid){
        File playerStorageFile = getPlayerStorageFile(uuid);
        return YamlConfiguration.loadConfiguration(playerStorageFile);
    }

    private File getPlayerStorageFile(UUID uuid){
        File storageDirectory = getStorageDirectory();
        File playerStorageFile = new File(storageDirectory.getPath()+File.separator+uuid.toString()+".yml");
        if(!playerStorageFile.exists()){
            try
            {
                if(!playerStorageFile.createNewFile()){
                    throw new RuntimeException("Problem creating storage file for player "+uuid.toString()+"!");
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException("Problem creating storage file for player "+uuid.toString()+"!",e);
            }
        }
        return playerStorageFile;
    }

    private User loadUser(UUID uuid){
        FileConfiguration playerStorage = getPlayerStorage(uuid);
        String username = playerStorage.getString("username");
        User returned = new User(uuid,username);
        returned.setFrozen(playerStorage.getBoolean("frozen",false));
        returned.setFrozenUntil(playerStorage.getInt("frozenUntil",-1));
        return returned;
    }

    private void saveUser(User user){
        FileConfiguration playerStorage = getPlayerStorage(user.getUuid());
        playerStorage.set("uuid",user.getUuid());
        playerStorage.set("username",user.getUsername());
        playerStorage.set("frozen",user.isFrozen());
        playerStorage.set("frozen-until",user.getFrozenUntil());
        try
        {
            File playerStorageFile = getPlayerStorageFile(user.getUuid());
            playerStorage.save(playerStorageFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public User getUser(UUID uuid){
        User cachedUser = users.get(uuid);
        if(cachedUser !=null){
            return cachedUser;
        }
        else
        {
            User loadedUser = loadUser(uuid);
            if(loadedUser !=null){
                users.put(uuid, loadedUser);
            }
            return loadedUser;
        }
    }

    public User getUser(String username){
        Iterator<Map.Entry<UUID,User>> i = users.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry<UUID,User> current = i.next();
            User currentUser = current.getValue();
            if(currentUser.getUsername().equalsIgnoreCase(username)){
                return currentUser;
            }
        }
        return null;
    }

    public void updateUser(User user){
        users.put(user.uuid,null);
        users.put(user.uuid,user);
        saveUser(user);
    }

    private Main plugin;
    private static UserManager instance;
    private UserManager(Main plugin){
        this.plugin = plugin;
    }
    public static UserManager get()
    {
        if(instance == null){
            throw new IllegalStateException("You must call get() with the plugin as a parameter first!");
        }
        return instance;
    }
    public static UserManager get(Main plugin){
        if(instance == null){
            if(plugin == null){
                throw new IllegalArgumentException("Plugin cannot be null!");
            }
            instance = new UserManager(plugin);
        }
        return get();
    }
}