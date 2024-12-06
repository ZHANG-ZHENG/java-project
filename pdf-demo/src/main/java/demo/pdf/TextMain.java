package demo.pdf;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;


/**
 * 使用 Tesseract OCR 处理图像型 PDF
 */
public class TextMain {
    public static void main(String[] args) {
        String pdfFilePath = "F:/test.pdf";

        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            // 创建一个 PDFTextStripper 实例
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // 提取 PDF 中的文本
            String text = pdfStripper.getText(document);
            // 输出提取的文本
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

