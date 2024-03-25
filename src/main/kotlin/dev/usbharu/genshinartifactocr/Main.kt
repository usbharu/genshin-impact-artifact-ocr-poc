package dev.usbharu.genshinartifactocr


import net.sourceforge.tess4j.ITessAPI
import net.sourceforge.tess4j.Tesseract
import org.bytedeco.opencv.global.opencv_imgcodecs
import org.bytedeco.opencv.global.opencv_imgcodecs.imwrite
import org.bytedeco.opencv.global.opencv_imgproc.*
import org.bytedeco.opencv.opencv_core.Size
import java.io.File
import java.text.Normalizer
import javax.imageio.ImageIO

fun main() {
    Thread.currentThread().contextClassLoader
    File("images").listFiles { dir, name -> name.endsWith(".webp") or name.endsWith(".png") }.forEach { ocr(it.absoluteFile) }



}

val subOptionParser = SubOptionParser()

val positive = listOf(
    "会心率",
    "会心ダメージ",
    "防御",
    "攻撃",
    "元素熟知",
    "元素チャージ効率",
    "HP",
    "会心",
    "攻",
    "元素チャージ",
    "チャージ",
    "チヤージ",
    "P+",
    "+"
)

val negative = listOf("2セット","通常攻撃","HPが","秒","セット","重撃")

private fun ocr(s: File) {
    val contextClassLoader = Thread.currentThread().contextClassLoader


    val imread = opencv_imgcodecs.imread(s.absolutePath)


    val size = imread.size()
    resize(imread,imread, Size(size.width()*2,size.height()*2),0.0,0.0, INTER_CUBIC)

    cvtColor(imread, imread, COLOR_BGR2GRAY)

    GaussianBlur(imread, imread, Size(5, 5), 0.0)

    adaptiveThreshold(imread,imread,255.0, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY,31,2.0)

    val s1 = "processed_" + s.name
    imwrite(s1,imread)

    val image = ImageIO.read(File(s1))

    requireNotNull(image)

    val tesseract = Tesseract()

    tesseract.setDatapath(
        File(
            contextClassLoader.getResource("jpn.traineddata").toURI()
        ).absoluteFile.parent.toString()
    )
    tesseract.setLanguage("jpn")
    tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY)

    val doOCR = tesseract.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_TEXTLINE)
        .map { it.text }
        .map { it.replace(" ", "").replace("\n", "").replace("・","") }
        .map { Normalizer.normalize(it, Normalizer.Form.NFKC) }


    println(s)
    println(doOCR)
    val message = doOCR.filter { positiveFilter(it) }.filterNot { negativeFilter(it) }
    println(message)
    val artifactOptions = message.mapNotNull { subOptionParser.parse(it) }

    val score = ComputeArtifactScore().compute(artifactOptions, ComputeOptions.Attack)

    println("Score: $score")
}

private fun positiveFilter(string: String):Boolean{
    return positive.any {
        it in string
    }
}

private fun negativeFilter(string: String):Boolean{
    return negative.any {
        it in string
    }
}