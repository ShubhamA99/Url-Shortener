package com.Shubham.Url_Shortener.ZookeeperConfig;

import org.apache.zookeeper.KeeperException;

public interface ZKManager {

    public void create(String path, byte[] data)
            throws KeeperException, InterruptedException;
    public Long getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException;
    public void update(String path, byte[] data)
            throws KeeperException, InterruptedException;
}
