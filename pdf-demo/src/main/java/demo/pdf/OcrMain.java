package demo.pdf;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 提取图片 ocr
 * https://github.com/tesseract-ocr/tesseract
 * 语言包 https://github.com/tesseract-ocr/tessdata
 */
public class OcrMain {
    public static void main(String[] args) {
        String pdfFilePath = "F:/test.pdf";

        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            ITesseract tesseract = new Tesseract();

            // 设置 Tesseract 数据路径（语言包路径）
            tesseract.setDatapath("F:/tessdata"); // https://github.com/tesseract-ocr/tessdata
            tesseract.setLanguage("eng+chi_sim"); // 设置语言，使用 eng（英文）或 chi_sim（简体中文）

            StringBuilder resultText = new StringBuilder();

            // 遍历 PDF 的每一页
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                System.out.println("page:" + page);
                // 将每一页渲染为图像
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300); // 使用 300 DPI
                // 识别图像中的文字
                String text = tesseract.doOCR(image);
                resultText.append(text).append("\n");
            }

            // 输出识别的文字
            System.out.println(resultText.toString());
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }
}

