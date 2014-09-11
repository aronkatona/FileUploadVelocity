package com.aronkatona.controllerFileUploadVelocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aronkatona.model.Item;
import com.aronkatona.service.ItemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private ItemService itemService;

	@Autowired(required = true)
	@Qualifier(value = "itemService")
	public void setItemService(ItemService is) {
		this.itemService = is;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadFileForm() {
        return "uploadfile";
    }
 
   
	
	@RequestMapping(value = "/saveFiles", method = RequestMethod.POST)
    public String fileSave( @RequestParam("file") MultipartFile[] files ) throws IllegalStateException, IOException{
		
		String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();
        String saveDirectory = "F:/servers/apache-tomcat-8.0.9/tmpFiles/";
	
	
        for (int i = 0; i < files.length; ++i) {
        	
        	//server
            String fileName = files[i].getOriginalFilename();
            if (!"".equalsIgnoreCase(fileName)) {
            	files[i].transferTo(new File(saveDirectory + fileName));
            }
            
            //db
            Item item = new Item();
        	item.setImgAddress(files[i].getOriginalFilename());
        	item.setName("asd");
        	this.itemService.saveItem(item);
        	
        }
        
        

        
		return "uploadSuccess";
	}
    
    @RequestMapping(value="/files")
    public String files(Model model){
    	
    	File folder = new File("F:/servers/apache-tomcat-8.0.9/tmpFiles/");
    	List<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
    	
    	model.addAttribute("files", files);
    	
    	return "files";
    }
    
    @RequestMapping(value="/downloadFile.{fileName}")
    public void downloadFile(@PathVariable String fileName,HttpServletResponse response,  HttpServletRequest request){
    	
    	
    	File folder = new File("F:/servers/apache-tomcat-8.0.9/tmpFiles/");
    	File[] listOfFiles = folder.listFiles();
    	File file = null;
    	    for (int i = 0; i < listOfFiles.length; i++) {
    	    	if(listOfFiles[i].getName().equals(fileName)){
    	    		System.out.println("Megvan a file: " + fileName);
    	    		file = listOfFiles[i];
    	    	}
    	    	else if (listOfFiles[i].isFile()) {
    	        System.out.println("File " + listOfFiles[i].getName());
    	      } else if (listOfFiles[i].isDirectory()) {
    	        System.out.println("Directory " + listOfFiles[i].getName());
    	      }
    	    }
    	    
    	    if(file != null){

    	        try {
    	            response.setContentType("application/txt");
    	            response.setContentLength(new Long(file.length()).intValue());
    	            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
    	            FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

    	        } catch (Exception e) {

    	            e.printStackTrace();
    	        }
    	    }


    }
	
}
