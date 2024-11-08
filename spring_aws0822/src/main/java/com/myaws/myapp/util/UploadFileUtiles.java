package com.myaws.myapp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;


public class UploadFileUtiles {
	
	  // 로그 출력 객체 생성
	private static final Logger logger = 
			LoggerFactory.getLogger(UploadFileUtiles.class);

	 // 파일 업로드 메서드
	public static String uploadFile(String uploadPath,
									String originalName,byte[] fileData	)	
	throws Exception{
		
		// UUID를 이용해 파일 이름을 중복 없이 생성 앞에 랜덤번호가 생성되고 뒤에 찐 이름이 나온다. 
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() +"_"+originalName;
		
//		String path = UploadFileUtiles.class.getResource("").getPath();
//		System.out.println("현재클래스path:"+path);
		
//  	실행되는 시스템 위치	
//		System.out.println(System.getProperty("user.dir"));
       
//        String realpath = request.getSession().getServletContext().getRealPath(uploadPath);
//		System.out.println("realpath:"+realpath);
		
		 // 파일이 저장될 경로 계산
		String savedPath = calcPath(uploadPath);
		
		// 실제 파일 저장 위치를 생성하여 파일 복사
		File target = new File(uploadPath+savedPath,savedName);
//  	등록한 파일 상대경로
//		String loc = target.getCanonicalPath();

		FileCopyUtils.copy(fileData,target);
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		System.out.println("formatName:"+formatName);
		String uploadedFileName = null;
		
		if(MediaUtils.getMediaType(formatName) != null){
			uploadedFileName = makeThumbnail(uploadPath,savedPath, savedName);
		}else{
			uploadedFileName = makeIcon(uploadPath,savedPath,savedName);
		}
				
		// 2018/05/03/s-dssddssf-2323423.jpg
		return uploadedFileName;
	}	
	
	// 메서드의 역할은 실행하면 저장할 폴더를 만들고(savaPath), 썸네일을 만들고(makeThumbnail), uploadedFileName으로 리턴하도록 하는 역할
	// 유틸이니까 사용법? 정도만 알면된다.

	
	
	// 아이콘 만드는 메서드 
	private  static String makeIcon(String uploadPath,
			String path,
			String fileName)throws Exception{

		String iconName = uploadPath+path+File.separator+fileName;				
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	
	// 저장할 파일의 날짜를 뽑아내서
	private static String calcPath(String uploadPath){	
		
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator+cal.get(Calendar.YEAR); //연도를 뽑아내고
		
		String monthPath = yearPath+
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1); //월을 뽑아내고
		
		String datePath = monthPath +
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.DATE)); //시간을 뽑아낸다.
			
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		logger.info(datePath);
		
		return datePath;
	}
	
	// 폴더 생성시켜주는 메서드
	private static void makeDir(String uploadPath,String...paths){ 
			
		if(new File(uploadPath+paths[paths.length -1]).exists())
			return;
		
		for(String path : paths){
			
			File dirPath = new File(uploadPath + path);		
	//		System.out.println("dirPath:"+dirPath);			
			
			if (! dirPath.exists()){			
				dirPath.mkdir();				
			}
		}
	}
	
	
	
	// 썸네일을 만들어주는 메서드 
	private static String makeThumbnail(String uploadPath,
			String path,
			String fileName) throws Exception{
		
		BufferedImage sourceImg = 
				ImageIO.read(new File(uploadPath+path,fileName));
		BufferedImage destImg = 
				Scalr.resize(sourceImg, 
						Scalr.Method.AUTOMATIC, 
						Scalr.Mode.FIT_TO_HEIGHT,100);
		
		String thumbnailName = 
				uploadPath + 
				path + 
				File.separator + 
				"s-"+fileName;
	//separator는 구분자인데 왜 이걸로 했냐면 운영체제마다 다르기 때문에 저걸로 통일?했다.
	//	System.out.println("thumbnailName"+thumbnailName);
		
		File newFile = new File(thumbnailName);
	//	System.out.println("newFile:"+newFile);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
	
	//	System.out.println("destImg"+destImg);
		boolean flag = ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		System.out.println("복사여부 flag"+flag);
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}	
}
