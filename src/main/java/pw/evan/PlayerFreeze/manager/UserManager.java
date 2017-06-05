package pw.evan.PlayerFreeze.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pw.evan.PlayerFreeze.Main;
import pw.evan.PlayerFreeze.model.User;
import pw.evan.PlayerFreeze.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager
{

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
        if(username==null){
            return null;
        }
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

    public User getUser(Player player)
    {
        return getUser(player.getUniqueId());
    }

    private HashMap<UUID,User> loadAllUsers(){
        File storageDirectory = getStorageDirectory();
        File[] userStorageFiles = storageDirectory.listFiles();
        HashMap<UUID, User> returned = new HashMap<>();
        if(userStorageFiles != null)
        {
            for (File current : userStorageFiles)
            {
                String extension = FileUtil.getFileExtension(current);
                if(extension.equalsIgnoreCase("yml")){
                    String fileName = FileUtil.stripExtension(current);
                    if(FileUtil.validateUUID(fileName)){
                        UUID uuid = UUID.fromString(fileName);
                        User loaded = loadUser(uuid);
                        if(loaded != null){
                            returned.put(uuid,loaded);
                        }
                    }
                }
            }
        }
        return returned;
    }

    private User searchUserFiles(String username){
        File storageDirectory = getStorageDirectory();
        File[] userStorageFiles = storageDirectory.listFiles();
        User returned;
        if(userStorageFiles != null)
        {
            for (File current : userStorageFiles)
            {
                String extension = FileUtil.getFileExtension(current);
                if(extension.equalsIgnoreCase("yml")){
                    String fileName = FileUtil.stripExtension(current);
                    if(FileUtil.validateUUID(fileName)){
                        UUID uuid = UUID.fromString(fileName);
                        User loaded = loadUser(uuid);
                        if(loaded != null){
                            if(loaded.getUsername().equalsIgnoreCase(username)){
                                return loaded;
                            }
                        }
                    }
                }
            }
        }
        return null;
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
                cacheUser(loadedUser);
            }
            return loadedUser;
        }
    }

    private Player getOnlinePlayer(String username){
        return plugin.getServer().getPlayer(username);
    }

    private void cacheUser(User user){
        users.put(user.getUuid(),user);
    }

    public void uncacheUser(UUID uuid){
        users.put(uuid, null);
    }

    public User getUser(String username){
        Player onlinePlayer = getOnlinePlayer(username);
        if(onlinePlayer!=null)
        {
            return getUser(onlinePlayer.getUniqueId());
        }
        else
        {
            for (Map.Entry<UUID, User> current : users.entrySet())
            {
                User currentUser = current.getValue();
                if (currentUser.getUsername().equalsIgnoreCase(username))
                {
                    return currentUser;
                }
            }
            User loaded = searchUserFiles(username);
            if(loaded!=null){
                cacheUser(loaded);
            }
            return loaded;
        }
    }

    public void updateUser(User user){
        cacheUser(user);
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
