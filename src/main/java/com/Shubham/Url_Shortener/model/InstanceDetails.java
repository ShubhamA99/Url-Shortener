package com.Shubham.Url_Shortener.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
public class InstanceDetails {

    @Value("${instance.name}")
    private  String instanceName;

    @Value("${instance.minLimit}")
    private Long instanceMinLimit;

    @Value("${instance.maxLimit}")
    private Long instanceMaxLimit;

    @Value("${instance.APIPath}")
    private String ApiURL;

    @Value("${instance.parentFolderName}")
    private String InstanceParentFolderName;


}
