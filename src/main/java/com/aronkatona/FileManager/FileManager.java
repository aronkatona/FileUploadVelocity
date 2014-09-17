package com.aronkatona.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aronkatona.model.Item;
import com.aronkatona.service.ItemService;

public class FileManager {
	
	private final String DIRECTORY = "F:/springMVC/FileUploadVelocity/src/main/webapp/resources/files/";
	private final Integer MAXSIZEOFFILE = 20971520; //20mb
	
	private ItemService itemService;

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public FileManager(){
		String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();
	}
	
	public void uploadFiles(String[] itemNames, String[] itemOwners, String[] buyDates, MultipartFile[] files) throws IllegalStateException, IOException {
		
        String saveDirectory = DIRECTORY;
	
	
        for (int i = 0; i < files.length; ++i) {
        	
        	//server
            String fileName = files[i].getOriginalFilename();
            if (!"".equalsIgnoreCase(fileName) && !fileName.equals("") && files[i].getSize() < MAXSIZEOFFILE) {
            	files[i].transferTo(new File(saveDirectory + fileName));
            	
            	 //db
                Item item = new Item();
                item.setName(itemNames[i]);
                item.setOwner(itemOwners[i]);
                item.setBuyTime(new Date(buyDates[i]));
            	item.setImgAddress(files[i].getOriginalFilename());
            	this.itemService.saveItem(item);
            }
            
           
        	
        }
	}
	
	public void downloadFiles(String fileName,HttpServletResponse response){
		
		File folder = new File(DIRECTORY);
    	File[] listOfFiles = folder.listFiles();
    	File file = null;
    	    for (int i = 0; i < listOfFiles.length; i++) {
    	    	if(listOfFiles[i].getName().equals(fileName)){
    	    		file = listOfFiles[i];
    	    		break;
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
	
	public void deleteFile(int itemId){
		
		
		Item item = this.itemService.getItemById(itemId);
	    this.itemService.removeItem(itemId);
		
		File folder = new File(DIRECTORY);
    	File[] listOfFiles = folder.listFiles();
    	File file = null;
    	    for (int i = 0; i < listOfFiles.length; i++) {
    	    	if(listOfFiles[i].getName().equals(item.getImgAddress())){
    	    		file = listOfFiles[i];
    	    		file.delete();
    	    		break;
    	    	}
    	    }
    	    
    	    
		
	}
	
	public File getFile(String fileName){
		File folder = new File(DIRECTORY);
    	File[] listOfFiles = folder.listFiles();
    	    for (int i = 0; i < listOfFiles.length; i++) {
    	    	if(listOfFiles[i].getName().equals(fileName)){
    	    		return listOfFiles[i];
    	    	}
    	    }
    	    return null;
	}
	

}
