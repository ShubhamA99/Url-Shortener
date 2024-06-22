package com.Shubham.Url_Shortener.Controller;


import com.Shubham.Url_Shortener.Service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/us")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;



    @PostMapping("/postUrl")
    public ResponseEntity<String> shortenUrl(HttpServletRequest request){
        String url = request.getParameter("url");
        String shortUrl =urlShortenerService.bigToShortUrl(url);
        return ResponseEntity.ok().body(shortUrl);
    }



    @GetMapping("/{encodedUrl}")
    public RedirectView redirectWithUsingRedirectView(@PathVariable("encodedUrl") final String encodedUrl) {
        String ogUrl =urlShortenerService.getOriginalUrl(encodedUrl);
        if(ogUrl == null){
            return  new RedirectView("");
        }
        return new RedirectView(ogUrl);
    }

}
