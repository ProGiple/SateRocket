package org.satellite.progiple.saterocket.holo;

import org.bukkit.Location;

public interface HoloIAPI {
    void remove(String id);
    String create(Location location);
    boolean isEnabled();
    void setEnabled(boolean val);
}
