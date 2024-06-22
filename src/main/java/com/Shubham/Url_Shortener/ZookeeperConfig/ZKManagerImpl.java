package com.Shubham.Url_Shortener.ZookeeperConfig;

import com.Shubham.Url_Shortener.Util.Util;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class ZKManagerImpl implements  ZKManager{

    private static ZooKeeper zkeeper;
    private static ZKConnection zkConnection;



    public ZKManagerImpl() {
        initialize();
    }

    private void initialize() {
        zkConnection = new ZKConnection();
        try {
            zkeeper = zkConnection.connect("localhost:2181");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            zkConnection.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(String path, byte[] data)
            throws KeeperException,
            InterruptedException {

        zkeeper.create(
                path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    public Long getZNodeData(String path, boolean watchFlag)
            throws KeeperException,
            InterruptedException {

        byte[] b = null;
        b = zkeeper.getData(path, null, null);
        Long count  = null;
        try {
            String value =  new String(b,"utf-8");
            count = Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  count;
    }

    public void update(String path, byte[] data) throws KeeperException,
            InterruptedException {
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }

}
