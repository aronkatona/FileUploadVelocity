package com.aronkatona.controllerFileUploadVelocity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aronkatona.FileManager.FileManager;
import com.aronkatona.model.Item;
import com.aronkatona.service.ItemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private ItemService itemService;
	private FileManager fileManager = new FileManager();

	@Autowired(required = true)
	@Qualifier(value = "itemService")
	public void setItemService(ItemService is) {
		this.itemService = is;
		this.fileManager.setItemService(is);
	}
	
	@RequestMapping(value="/error404")
	public String errorPage(Locale locale){
		return "error";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadFileForm() {
        return "uploadItem";
    }
   
	@RequestMapping(value = "/saveFiles", method = RequestMethod.POST)
    public String uploadFile( @RequestParam("name") String[] itemNames,
    						  @RequestParam("owner") String[] itemOwners,
    						  @RequestParam("date") String[] buyDates,
    						  @RequestParam("file") MultipartFile[] files) throws IllegalStateException, IOException{
		
		fileManager.uploadFiles(itemNames,itemOwners,buyDates,files);
		return "uploadSuccess";
	}
    
    @RequestMapping(value="/files")
    public String files(Model model){
    	
    	File folder = new File("F:/springMVC/FileUploadVelocity/src/main/webapp/resources/files");
    	List<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
    	model.addAttribute("files", files);
    	
    	String[] fileNames = new String[files.size()];
    	int i = 0;
    	for(File file : files){
    		fileNames[i++] = file.getName();
    	}
    	model.addAttribute("fileNames", fileNames);
    	
    	return "files";
    }
    
    @RequestMapping(value="/items")
    public String items(Model model,HttpSession session){
    	
    	if(session.getAttribute("editItemId") != null && !session.getAttribute("editItemId").equals("")){
    		Item item = this.itemService.getItemById(Integer.parseInt(session.getAttribute("editItemId").toString()));
    		model.addAttribute("editItem", "editItem");
    		model.addAttribute("item", item);
    		session.setAttribute("editItemId", "");
    	}
    	
    	List<Item> items = this.itemService.getItems();
    	model.addAttribute("items", items);
    	return "items";
    }
    
    @RequestMapping(value="/editItem.{itemId}")
    public String editItem(@PathVariable int itemId,HttpSession session){
    	session.setAttribute("editItemId", itemId);
    	return "redirect:/items";
    }
    
    @RequestMapping(value="/editItemExecute.{itemId}")
    public String editItemExecute(@PathVariable int itemId ,@RequestParam Map<String, String> reqPar){
    	Item item = this.itemService.getItemById(itemId);
    	item.setName(reqPar.get("name"));
    	item.setOwner(reqPar.get("owner"));
    	//item.setBuyTime(Cal);
    	item.setBuyTime(new Date(reqPar.get("date")));
    	this.itemService.updateItem(item);
    	
    	return "redirect:/items";
    }

    @RequestMapping(value="/deleteItem.{itemId}")
    public String deleteItem(@PathVariable int itemId){
    	fileManager.deleteFile(itemId);  	
    	return "redirect:/items";
    }
    
    
    
    @RequestMapping(value="/downloadFile.{fileName}")
    public void downloadFile(@PathVariable String fileName,HttpServletResponse response,  HttpServletRequest request){    	
    	fileManager.downloadFiles(fileName, response);

    }

}
