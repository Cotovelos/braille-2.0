package com.cotovelos.braille2.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
 
/**
 * Service which creates a QR code in the GS1 pattern (ID, Category, URL)
 * 
 * @author Charles.Vatin
 *
 */
public class QRCodeService {

	private String myCodeText; // Ex: "(21)10\0(91)peixe\0(8200)http://google.com.br";
	private String filePath; // Ex: "C:/Users/gabri/Desktop/testeQR.png";
	private int size; // Ex: 250
	private String fileType; // Ex: png, gif.

	public QRCodeService(String myCodeText, String filePath, String fileType, int size)
	{
		this.myCodeText = myCodeText;
		this.filePath = filePath;
		this.fileType = fileType;
		this.size = size;
	}

	/**
	 * Creates the QRCode
	 * 
	 */
	public int GerarQRCode()
	{		
		File myFile = new File(this.filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(this.myCodeText, BarcodeFormat.QR_CODE, this.size,
					this.size, hintMap);
			int imgWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(imgWidth, imgWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, imgWidth, imgWidth);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < imgWidth; i++) {
				for (int j = 0; j < imgWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
}
