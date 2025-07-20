package com.example.board.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.board.AttachedFile;

@Component
public class FileService {
	
	@Value("${file.upload.path}")
	private String uplaodPath;

	public AttachedFile savedFile(MultipartFile mfile) {
		
		System.out.println("uplaodPath : " + uplaodPath);
		
		if(mfile == null || mfile.getSize() == 0) {
			return null;
		}
		
		File path = new File(uplaodPath);
		if(!path.isDirectory()) {
			path.mkdir();
		}
		
		String originalFilename = mfile.getOriginalFilename();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savedFilename = sdf.format(new Date());
		
		String ext;
		int lastIndex = originalFilename.lastIndexOf(".");
		
		if(lastIndex == -1) {
			ext = "";
		} else {
			ext = "." + originalFilename.substring(lastIndex + 1);
		}
		
		File serverFile = null;
		
		while(true) {
			serverFile = new File(uplaodPath + "/" + savedFilename + ext);
			if(!serverFile.isFile()) break;
			savedFilename = savedFilename + new Date().getTime();
		}
		
		try {
			mfile.transferTo(serverFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new AttachedFile(originalFilename, savedFilename + ext, mfile.getSize());
	}
}
