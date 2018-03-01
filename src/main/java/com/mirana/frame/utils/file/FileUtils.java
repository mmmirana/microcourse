package com.mirana.frame.utils.file;

import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.date.DatePattern;
import com.mirana.frame.utils.date.DateUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;

public class FileUtils {

	/**
	 * 判断文件是否存在
	 *
	 * @param filepath
	 * @return
	 */
	public static boolean isExist (String filepath) {
		Assert.hasText(filepath);
		return isExist(new File(filepath));
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param file
	 * @return
	 */
	public static boolean isExist (File file) {
		return file.exists();
	}

	/**
	 * 是否是文件，文件存在且是文件返回true，其他返回false
	 *
	 * @param filepath
	 * @return
	 */
	public static boolean isFile (String filepath) {
		boolean isFile = false;
		if (!isExist(filepath)) {
			LogUtils.warn("File: " + filepath + " is not exist!");
		} else {
			isFile = isFile(new File(filepath));
		}
		return isFile;
	}

	/**
	 * 是否是文件，文件存在且是文件返回true，其他返回false
	 *
	 * @param file
	 * @return
	 */
	public static boolean isFile (File file) {
		boolean isFile = false;
		if (!isExist(file)) {
			LogUtils.warn("File: " + file + " is not exist!");
		} else {
			if (file.isFile()) {
				isFile = true;
			}
		}
		return isFile;
	}

	/**
	 * 是否是文件目录，文件存在且是文件目录返回true，其他返回false
	 *
	 * @param filepath
	 * @return
	 */
	public static boolean isDirectory (String filepath) {
		boolean isFile = false;
		if (!isExist(filepath)) {
			LogUtils.warn("File: " + filepath + " is not exist!");
		} else {
			if (new File(filepath).isDirectory()) {
				isFile = true;
			}
		}
		return isFile;
	}

	/**
	 * 是否是文件目录，文件存在且是文件目录返回true，其他返回false
	 *
	 * @param file
	 * @return
	 */
	public static boolean isDirectory (File file) {
		boolean isFile = false;
		if (!isExist(file)) {
			LogUtils.warn("File: " + file + " is not exist!");
		} else {
			if (file.isDirectory()) {
				isFile = true;
			}
		}
		return isFile;
	}

	/**
	 * 如果不存在对应的目录，创建目录
	 *
	 * @param dirpath
	 */
	public static void mkdirs (String dirpath) {
		Assert.hasText(dirpath);
		mkdirs(new File(dirpath));
	}

	/**
	 * 如果不存在对应的目录，创建目录
	 *
	 * @param file
	 */
	private static void mkdirs (File file) {
		if (!file.exists() || !file.isDirectory()) {
			LogUtils.info("准备创建目录: " + file.getAbsolutePath());
			file.mkdirs();
		}
	}

	/**
	 * 替换文件后缀
	 *
	 * @param filename
	 * @param targetSuffix
	 * @return
	 */
	public static String replaceSuffix (String filename, String targetSuffix) {
		int dotindex = filename.lastIndexOf(".");
		if (dotindex >= 0) {
			return filename.substring(0, dotindex) + targetSuffix;
		} else {
			return null;
		}
	}

	/**
	 * 获取文件路径获取文件名称
	 * <p>
	 * <br>
	 * 如："F:/wrcold520/progress.png"返回progress.png
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFilename (String filePath) {
		String fileName = "";
		int firstIndex = 0;
		int secIndex = 0;
		if (filePath.contains("\\")) {
			firstIndex = filePath.lastIndexOf("\\");
		}
		if (filePath.contains("/")) {
			secIndex = filePath.lastIndexOf("/");
		}
		int slashIndex = Math.max(firstIndex, secIndex);
		if (slashIndex != 0) {
			fileName = filePath.substring(slashIndex + 1);
		}
		return fileName;
	}

	/**
	 * 获取文件前缀
	 * <p>
	 * <br>
	 * 如："progress.png"返回progress
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefix (String fileName) {
		String prefix = fileName;
		if (fileName.contains(".")) {
			int dotIndex = fileName.lastIndexOf(".");
			prefix = fileName.substring(0, dotIndex);
		}
		return prefix;
	}

	/**
	 * 获取文件后缀
	 * <p>
	 * <br>
	 * 如："progress.png"返回".png"
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix (String fileName) {
		String suffix = "";
		if (fileName.contains(".")) {
			int dotIndex = fileName.lastIndexOf(".");
			suffix = fileName.substring(dotIndex);
		}
		return suffix;
	}

	/**
	 * 根据原始文件名称，生成不同类型的文件名称
	 *
	 * @param type        文件名称的类型
	 * @param orgFileName 原始文件名称
	 * @return
	 */
	public static String generateFilename (String orgFileName, FileNameType type) {
		// 获取原始文件前缀
		String orgFilePrefix = getFilePrefix(orgFileName);
		// 获取原始文件后缀
		String orgFileSuffix = getFileSuffix(orgFileName);
		// 获取当前时间戳
		String timestamp = DateUtils.format(new Date(), DatePattern.DATETIME_NOSYMBOL);

		// 最终的文件名称
		String finalFileName = null;

		switch (type) {
			case OrgFilename:
				finalFileName = orgFileName;
				break;
			case TimeStamp:
				finalFileName = timestamp + orgFileSuffix;
				break;
			case OrgAndTimeStamp:
				finalFileName = orgFilePrefix + timestamp + orgFileSuffix;
				break;
			case TimeStampAndOrg:
				finalFileName = timestamp + orgFileName;
				break;
			default:
				// 默认是原文件名
				finalFileName = orgFileName;
				break;
		}
		return finalFileName;

	}

	/**
	 * 将MultipartFile存储到toFilePath目标文件
	 *
	 * @param file
	 * @param toFilePath
	 * @return true：上传成功；false：上传失败。
	 */
	public static boolean uploadMultiFile (MultipartFile file, String toFilePath) {
		File toFile = new File(toFilePath);
		return uploadMultiFile(file, toFile);
	}

	/**
	 * 将MultipartFile存储到toFile目标文件
	 *
	 * @param file
	 * @param toFile
	 * @return true：上传成功；false：上传失败。
	 */
	public static boolean uploadMultiFile (MultipartFile file, File toFile) {
		File folder = toFile.getParentFile();
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			file.transferTo(toFile);
			return true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * springmvc文件下载
	 *
	 * @param targetFile 要下载的文件
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downloadFile (File targetFile) {
		try {
			// 获取下载时默认的文件名称
			String defaultName = java.net.URLEncoder.encode(targetFile.getName(), "UTF-8");
			HttpHeaders headers = new HttpHeaders();
			// 设置ContentType
			MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
			headers.setContentType(mediaType);
			headers.setContentDispositionFormData("attachment", defaultName);
			// 读取文件内容
			byte[] content = org.apache.commons.io.FileUtils.readFileToByteArray(targetFile);
			ResponseEntity<byte[]> result = new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 普通文件下载（没有返回值，直接输出流）
	 */
	public static void downloadFile (HttpServletResponse response, File targetFile) {
		OutputStream out = null;

		try {
			String defaultName = java.net.URLEncoder.encode(targetFile.getName(), "UTF-8");
			// response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + defaultName);
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
		}

		try {
			out = response.getOutputStream();
			out.write(org.apache.commons.io.FileUtils.readFileToByteArray(targetFile));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据给出的父节点folder找到所有的file，如果不是folder，则返回size为0的集合。
	 *
	 * @param parentFile
	 * @return
	 */
	public static List<File> getChildFiles (File parentFile) {
		List<File> list = new ArrayList<File>();
		// 如果是folder，则遍历
		if (parentFile.isDirectory()) {
			File[] files = parentFile.listFiles();
			if (files == null || files.length == 0) {
				return list;
			}
			for (File file : files) {
				list.add(file);
			}
			// 排序
			sortByName(list);

			List<File> folderList = new ArrayList<File>();
			List<File> fileList = new ArrayList<File>();

			// 将得到的文件集合分为文件夹和文件两种类型
			for (File file : list) {
				if (file.isDirectory()) {
					folderList.add(file);
				} else {
					fileList.add(file);
				}
			}

			list.clear();
			list.addAll(folderList);
			list.addAll(fileList);
		}

		return list;
	}

	/**
	 * 删除路径下所有的文件和文件夹
	 *
	 * @param userDataFolder
	 */
	public static void deleteByParentPath (File userDataFolder) {
		if (userDataFolder.exists()) {
			if (userDataFolder.isFile()) {
				userDataFolder.delete();
				LogUtils.info("删除文件：" + userDataFolder);
			} else {
				File[] files = userDataFolder.listFiles();
				for (File childFile : files) {
					deleteByParentPath(childFile);
				}
			}
		}
	}

	/**
	 * 根据当前文件路径找到上级路径
	 *
	 * @param filePath
	 * @return
	 */
	public static String getParentFilePath (String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		} else {
			return file.getParent();
		}

	}

	/**
	 * 根据文件名称对文件list集合进行排序，先排序文件夹，再排序文件
	 *
	 * @param fileList
	 */
	public static void sortByName (List<File> fileList) {

		Collections.sort(fileList, new Comparator<File>() {

			public int compare (File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});

	}

	/**
	 * 文件复制（通过文件通道FileChannel更加高效），目标文件不存在将会创建
	 *
	 * @param fromPath from文件路径
	 * @param toPath   to文件路径
	 */
	public static void copyFileByChannel (String fromPath, String toPath) {
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		copyFileByChannel(fromFile, toFile);
	}

	/**
	 * 文件复制（通过文件通道FileChannel更加高效），目标文件不存在将会创建
	 *
	 * @param fromFile
	 * @param toFile
	 */
	public static void copyFileByChannel (File fromFile, File toFile) {
		if (!fromFile.exists()) {
			LogUtils.info("源文件： " + fromFile + "不存在，复制失败！");
			return;
		}
		if (!toFile.exists()) {
			createNewFile(toFile);
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel channelIn = null;
		FileChannel channelOut = null;
		try {
			in = new FileInputStream(fromFile);
			out = new FileOutputStream(toFile);
			channelIn = in.getChannel();
			channelOut = out.getChannel();
			channelIn.transferTo(0, channelIn.size(), channelOut);
			LogUtils.info("copyFileByChannel: from [" + fromFile.getAbsolutePath() + "] to [" + toFile.getAbsolutePath()
				+ "] successfully!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (channelIn != null) {
					channelIn.close();
				}
				if (channelOut != null) {
					channelOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyFileByInputStream (InputStream input, String toPath) {
		LogUtils.info("fileCopyByInputStream()");

		createNewFile(toPath);

		InputStream in = input;
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(toPath));
			byte[] b = new byte[2048];
			int i;
			while ((i = in.read(b)) != -1) {
				out.write(b, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 以字节为单位读写文件内容
	 *
	 * @param filePath ：需要读取的文件路径
	 */
	public static void copyFileByByte (String filePath, File toFile) {
		File file = new File(filePath);
		// InputStream:此抽象类是表示字节输入流的所有类的超类。
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// FileInputStream:从文件系统中的某个文件中获得输入字节。
			ins = new FileInputStream(file);
			outs = new FileOutputStream(toFile);
			int temp;
			// read():从输入流中读取数据的下一个字节。
			while ((temp = ins.read()) != -1) {
				outs.write(temp);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
				if (outs != null) {
					outs.close();
				}
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}

	/**
	 * 以字符为单位读写文件内容
	 *
	 * @param filePath
	 */
	public static void copyFileByCharacter (String filePath, File toFile) {
		File file = new File(filePath);
		// FileReader:用来读取字符文件的便捷类。
		FileReader reader = null;
		FileWriter writer = null;
		try {
			reader = new FileReader(file);
			writer = new FileWriter(toFile);
			int temp;
			while ((temp = reader.read()) != -1) {
				writer.write((char) temp);
			}
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {

					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以行为单位读写文件内容
	 *
	 * @param filePath
	 */
	public static void copyFileByLine (String filePath, File toFile) {
		File file = new File(filePath);
		// BufferedReader:从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		try {
			// FileReader:用来读取字符文件的便捷类。
			bufReader = new BufferedReader(new FileReader(file));
			bufWriter = new BufferedWriter(new FileWriter(toFile));
			// buf = new BufferedReader(new InputStreamReader(new
			// FileInputStream(file)));
			String temp = null;
			while ((temp = bufReader.readLine()) != null) {
				bufWriter.write(temp + "\n");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
				if (bufWriter != null) {
					bufWriter.close();
				}
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}

	/**
	 * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件
	 *
	 * @param filePath
	 */
	public static void copyFileByBybeBuffer (String filePath, File toFile) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			// 获取源文件和目标文件的输入输出流
			in = new FileInputStream(filePath);
			out = new FileOutputStream(toFile);
			// 获取输入输出通道
			FileChannel fcIn = in.getChannel();
			FileChannel fcOut = out.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				// clear方法重设缓冲区，使它可以接受读入的数据
				buffer.clear();
				// 从输入通道中将数据读到缓冲区
				int r = fcIn.read(buffer);
				if (r == -1) {
					break;
				}
				// flip方法让缓冲区可以将新读入的数据写入另一个通道
				buffer.flip();
				fcOut.write(buffer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制文件夹
	 *
	 * @param fromPath 源文件夹的路径
	 * @param toPath   目标文件夹的路径
	 */
	public static void copyFolder (String fromPath, String toPath) {
		File fromDir = new File(fromPath);
		File toDir = new File(toPath);

		// 如果源文件夹不存在，则取消操作
		if (!fromDir.exists() || !fromDir.isDirectory()) {
			LogUtils.info("源文件不是文件夹，取消操作！");
			return;
		}

		// 如果目标文件夹不存在，则创建
		if (!toDir.exists()) {
			toDir.mkdirs();
			LogUtils.info("文件夹：[" + toPath + "] 创建成功");
		}

		// 文件夹内所有文件
		File[] innerFiles = fromDir.listFiles();

		for (File innerFile : innerFiles) {
			if (innerFile.isFile()) {
				String toFile = toDir.getAbsolutePath() + File.separator + innerFile.getName();
				copyFileByChannel(innerFile.getAbsolutePath(), toFile);
			} else {
				// 递归处理文件夹的操作
				String innerFromPath = innerFile.getAbsolutePath();
				String innerToPath = toDir.getAbsolutePath() + File.separator + innerFile.getName();
				copyFolder(innerFromPath, innerToPath);
			}
		}
		LogUtils.info("copyFolder: from [" + fromPath + "] to [" + toPath + "] successfully!");
	}

	/**
	 * B-->KB-->M-->G之间的转换
	 *
	 * @param size 传进来的字节数（B）
	 * @return 返回类似于“12.56 KB”的string格式
	 */
	public static String convertFileSize (long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	/**
	 * 将字符串写入文件
	 *
	 * @param str         要写入的string
	 * @param logFilePath 被写入的文件路径
	 */
	public static void writeStrToFilePath (String str, String logFilePath) {
		File logFile = new File(logFilePath);
		writeStrToFile(str, logFile);
	}

	/**
	 * 将字符串写入文件
	 *
	 * @param str     要写入的string
	 * @param logFile 被写入的文件
	 */
	public static void writeStrToFile (String str, File logFile) {
		if (!logFile.exists()) {
			createNewFile(logFile);
		}
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(logFile.getAbsoluteFile(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(str);
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件路径创建文件，如果存在，不做任何操作。 如果文件不存在，则创建（如果父级folder不存在，则创建）
	 *
	 * @param filePath
	 */
	public static void createNewFile (String filePath) {
		File targetFile = new File(filePath);
		createNewFile(targetFile);
	}

	/**
	 * 根据文件路径创建文件，如果存在，不做任何操作。 如果文件不存在，则创建（如果父级folder不存在，则创建）
	 *
	 * @param targetFile
	 */
	public static void createNewFile (File targetFile) {
		if (!targetFile.exists()) {
			File parentFolder = targetFile.getParentFile();
			if (!parentFolder.exists()) {
				parentFolder.mkdirs();
				LogUtils.info("文件夹 [" + parentFolder.getAbsolutePath() + " ]成功！");
			}
			try {
				targetFile.createNewFile();
				LogUtils.info("文件[ " + targetFile + " ]创建成功！");
			} catch (IOException e) {
				LogUtils.warn("文件[ ：" + targetFile + " ]创建失败！");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成文件名的枚举类型
	 *
	 * @author wangzf
	 * @see {@link FileUtils#generateFilename(String, FileNameType)}
	 * @see FileUtils
	 */
	public enum FileNameType {
		/**
		 * 原文件名前缀+后缀
		 */
		OrgFilename,
		/**
		 * 时间戳+后缀
		 */
		TimeStamp,
		/**
		 * 原文件名前缀+时间戳+后缀
		 */
		OrgAndTimeStamp,
		/**
		 * 时间戳+原文件名+后缀
		 */
		TimeStampAndOrg,
	}

}
