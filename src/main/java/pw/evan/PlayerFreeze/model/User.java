package pw.evan.PlayerFreeze.model;

import java.util.UUID;

public class User
{
    private UUID uuid;
    private String username;
    private boolean frozen = false;
    private long frozenUntil = -1;

    private User(UUID uuid, String username, boolean frozen, long frozenUntil)
    {
        this.uuid = uuid;
        this.setUsername(username);
        this.setFrozen(frozen);
        this.setFrozenUntil(frozenUntil);
    }

    public User(UUID uuid, String username, long frozenUntil)
    {
        this(uuid, username, true, frozenUntil);
    }

    public User(UUID uuid, String username, boolean frozen)
    {
        this(uuid, username, frozen, -1);
    }

    public User(UUID uuid, String username)
    {
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
        if (!frozen)
        {
            setFrozenUntil(-1);
        }
    }

    public void setFrozenUntil(long frozenUntil)
    {
        this.frozenUntil = frozenUntil;
        if (frozenUntil > 0)
        {
            this.frozen = true;
        }
        else
        {
            this.frozen = false;
        }
    }
}
