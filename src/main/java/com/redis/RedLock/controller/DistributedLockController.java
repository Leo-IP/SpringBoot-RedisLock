package com.redis.RedLock.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
public class DistributedLockController {

    @Autowired
    private RedissonClient redisson;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/occupyDistributedLock", method = RequestMethod.GET)
    public String occupyDistributedLock(RedirectAttributes redirectAttributes){
        RLock lock = null;
        try{
            //lock's key
            String key = "MF:DISTRIBUTEDLOCK:S:personId_1001";
            //Get the distributed lock instance
            lock = redisson.getLock(key);
            //Add lock and set automatically unlock it after 15 seconds
            lock.lock(15, TimeUnit.SECONDS);
            //The program runs
            TimeUnit.SECONDS.sleep(10);
            //Get the internet time
            URL url=new URL("http://www.google.com");
            URLConnection conn=url.openConnection();
            conn.connect();
            long dateL=conn.getDate();
            Date date=new Date(dateL);
            //print and return result
            System.out.println(date);
            redirectAttributes.addFlashAttribute("success",date);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //unlock
            if (lock != null) lock.unlock();
        }

        return "redirect:/";
    }

}
