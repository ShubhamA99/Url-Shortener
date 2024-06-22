package com.Shubham.Url_Shortener.Service;


import com.Shubham.Url_Shortener.Repo.UrlDetailsRepo;
import com.Shubham.Url_Shortener.Util.Util;
import com.Shubham.Url_Shortener.ZookeeperConfig.ZKManagerImpl;
import com.Shubham.Url_Shortener.model.InstanceDetails;
import com.Shubham.Url_Shortener.model.UrlDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UrlShortenerService {

//    @Autowired
//    private ZKManagerImpl zkManager;
    @Autowired
    private InstanceDetails instanceDetails;

    @Autowired
    private UrlDetailsRepo urlDetailsRepo;

    private final Lock lock = new ReentrantLock();




    @Transactional
    public  String  bigToShortUrl(String bigUrl){
        Long counter = null;
        String encodedUrl = null;
        String basePathOfShortUrl =instanceDetails.getApiURL();

        StringBuilder path = new StringBuilder();
        path.append(instanceDetails.getInstanceParentFolderName());
        path.append(instanceDetails.getInstanceName());


        lock.lock();
        ZKManagerImpl zkManager = new ZKManagerImpl();
        try {
            // TODO - add Check if returned counter is between max and mix limit
            counter = zkManager.getZNodeData(path.toString(), false);
            encodedUrl =  encodeToCustomBase64(counter);
            UrlDetails urlDetails = UrlDetails.builder()
                    .OriginalURL(bigUrl)
                    .encodedValue(encodedUrl)
                    .createdAt(LocalDateTime.now())
                    .build();
            urlDetailsRepo.save(urlDetails);
            counter++;
            byte[] b =String.valueOf(counter).getBytes();
            zkManager.update(path.toString(),b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            zkManager.closeConnection();
            lock.unlock();
        }


        return basePathOfShortUrl.concat(encodedUrl);

    }


    public String getOriginalUrl(String encodedUrl){
        Optional<UrlDetails> urlDetail = urlDetailsRepo.findByEncodedValue(encodedUrl);
        String ogUrl = null;
        if(urlDetail.isPresent()){
            ogUrl = urlDetail.get().getOriginalURL();
        }
        return  ogUrl;
    }


    private  String encodeToCustomBase64(long number) {
        char[] BASE64_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder sb = new StringBuilder();
        int base = BASE64_CHARS.length;

        while (number > 0) {
            sb.insert(0, BASE64_CHARS[(int) (number % base)]);
            number = number / base;
        }

        // Pad with leading zeros if necessary to ensure 7 characters
        while (sb.length() < 7) {
            sb.insert(0, BASE64_CHARS[0]); // Use the first character as padding
        }

        return sb.toString();
    }
}
