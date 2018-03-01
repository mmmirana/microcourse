package com.mirana.frame.utils.file;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.SysPropUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class ImageUtils {
	/**
	 * 创建一个缩略图
	 *
	 * @param srcImgPath    图像源文件地址
	 * @param outImgPath    图像目标文件地址
	 * @param widthOrHeight 宽度或者高度
	 * @param quality       质量（0-1）
	 */
	public static void thumbnail (String srcImgPath, String outImgPath, int widthOrHeight, float quality) {

		Assert.fileExist(srcImgPath);

		try {
			// 源文件地址
			Thumbnails.of(srcImgPath)
				// 指定宽度和高度
				.size(widthOrHeight, widthOrHeight)
				// 压缩质量
				.outputQuality(quality)
				// 输出文件
				.toFile(outImgPath);
		} catch (Exception e) {
			throw new UtilsException("生成缩略图时发生异常", e);
		}
	}

	/**
	 * 创建一个缩略图
	 *
	 * @param srcImgPath 图像源文件地址
	 * @param outImgPath 图像目标文件地址
	 * @param scale      放大或者缩小的倍数
	 * @param quality    质量（0-1）
	 */
	public static void thumbnail (String srcImgPath, String outImgPath, double scale, float quality) {

		Assert.fileExist(srcImgPath);

		try {
			// 源文件地址
			Thumbnails.of(srcImgPath)
				// 放大或者缩小的倍数
				.scale(scale)
				// 压缩质量
				.outputQuality(quality)
				// 输出文件
				.toFile(outImgPath);
		} catch (Exception e) {
			throw new UtilsException("生成缩略图时发生异常", e);
		}
	}

	/**
	 * 根据源文件创建缩略图
	 *
	 * @param srcImgDirpath 图像源文件所在目录地址
	 * @param outImgDirpath 图像目标文件所在目录地址
	 * @param widthOrHeight 宽度或者高度
	 * @param widthOrHeight 质量（0-1）
	 */
	public static void thumbnails (String srcImgDirpath, String outImgDirpath, int widthOrHeight, float quality) {

		Assert.fileExist(srcImgDirpath);

		File srcImgDir = new File(srcImgDirpath);
		File[] imageFiles = srcImgDir.listFiles();

		String[] imagePathList = new String[imageFiles.length];
		for (int i = 0; i < imageFiles.length; i++) {
			imagePathList[i] = imageFiles[i].getAbsolutePath();
		}

		File outImgDir = new File(outImgDirpath);
		if (!outImgDir.exists()) {
			outImgDir.mkdirs();
		}
		try {
			// 源文件地址
			Thumbnails.of(imagePathList)
				// 指定宽度和高度
				.size(widthOrHeight, widthOrHeight)
				// 压缩质量
				.outputQuality(quality)
				// 输出文件
				.toFiles(outImgDir, Rename.NO_CHANGE);
		} catch (Exception e) {
			throw new UtilsException("生成缩略图时发生异常", e);
		}
	}

	public static void squareThumbnail (String srcImgPath, String outFilepath, int widthOrHeight) {

		Assert.fileExist(srcImgPath);

		try {
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(srcImgPath));
			int heigth = sourceImg.getHeight();
			int width = sourceImg.getWidth();

			// 图片的宽高的最小值
			int minLength = Math.min(width, heigth);

			Thumbnails
				// 源文件地址
				.of(srcImgPath)
				// 中心区域剪裁
				.sourceRegion(Positions.CENTER, minLength, minLength)
				// 指定宽度和高度
				.size(widthOrHeight, widthOrHeight)
				// 压缩质量
				.outputQuality(0.5f)
				// 输出文件
				.toFile(outFilepath);

			System.out.println("生成缩略图成功！outFilepath：" + outFilepath);
		} catch (Exception e) {
			throw new UtilsException("生成缩略图时发生异常", e);
		}

	}

	public static void squareThumbnails (String srcImgDirpath, String outFileDirpath, int widthOrHeight) {

		Assert.fileExist(srcImgDirpath);

		File srcImgDir = new File(srcImgDirpath);
		File[] imageFiles = srcImgDir.listFiles();

		File outImgDir = new File(srcImgDirpath);
		if (!outImgDir.exists()) {
			outImgDir.mkdirs();
		}

		try {
			for (File imgFile : imageFiles) {
				String srcImgPath = imgFile.getAbsolutePath();
				String outImgPath = outFileDirpath + SysPropUtils.FILE_SEPARATOR + imgFile.getName();
				squareThumbnail(srcImgPath, outImgPath, widthOrHeight);
			}
		} catch (Exception e) {
			throw new UtilsException("生成缩略图时发生异常", e);
		}

	}

	public static void main (String[] args) {
		// String srcImgPath = "E:\\Thumbnails\\src";
		// String outFilepath = "E:\\Thumbnails\\dist";
		// squareThumbnails(srcImgPath, outFilepath, 200);
		String srcImgPath = "C:\\Users\\Administrator\\Desktop\\mcourse\\user.png";
		String outImgPath = "C:\\Users\\Administrator\\Desktop\\mcourse\\user_default.png";
		squareThumbnail(srcImgPath, outImgPath, 128);
	}
}
