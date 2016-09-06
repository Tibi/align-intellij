package tibi.align

import scala.io.Source
import collection.mutable.ArrayBuffer


object Align {

  def align(source: String, separator: String = ","): String = {
    val cells = readMatrix(source, separator)
    val widths = findColumnWidths(cells)
    //println("sizes = " + widths)
    val res = pad(cells, widths)
    // output should use the same line separator as the input
    val lineSep = getLineSeparator(source)
    val sep = separator match {
      case "," => ", "
      case "=" => " = "
      case _ => separator
    }
    printMatrix(res, sep, lineSep.getOrElse("\n"))
  }

  /**
   * Searches the given source for the first occurrence of "\r\n", "\r", or "\n" and returns it.
   * The source is not reset
   */
  def getLineSeparator(source: String): Option[String] = {
    val i = source.indexOf('\r')
    if (i == -1) {
      if (source.indexOf('\n') != -1) Some("\n") else None
    } else {
      Some(if (source.charAt(i + 1) == '\n') "\r\n" else "\r")
    }
  }

  def readMatrix(source: String, sep: String): Seq[Seq[String]] =
    source.lines.toList.map(split(_, sep).toList.map(_.trim))

  def split(in: String, sep: String): Seq[String] =
    if (sep.length > 1) in split sep else split(in, sep(0))
    
  
  /**
   * Splits a string by the given separator. Separators inside single or double quotes are ignored.
   */
  def split(in: String, Sep: Char): Seq[String] = {
    val res = ArrayBuffer[String]()
    val currStr = new StringBuffer()
    var inSingleQuotes = false
    var inDoubleQuotes = false
    for (c <- in) {
      val ignoreSep = inSingleQuotes || inDoubleQuotes
      var append = true
      c match {
        case '\'' => if (!inDoubleQuotes) inSingleQuotes = ! inSingleQuotes
        case 34   => if (!inSingleQuotes) inDoubleQuotes = ! inDoubleQuotes
        case Sep  => {
          if (!ignoreSep) {
            append = false
            res.append(currStr.toString)
            currStr.setLength(0)
          }
        }
        case _ =>
      }
      if (append) currStr.append(c)
    }
    res.append(currStr.toString)
    res
  }

  def splitTest() {
    val res = split("aie,eiu,a\" 'ee,aa'\" ,dl", ',')
    assert(res == List("aie", "eiu", "a\" 'ee,aa'\"", "dl"))
  }

  def findColumnWidths(cells: Seq[Seq[String]]): List[Int] = {
    var widths = List(0)
    for (l <- cells) {
      if (l.size > 1) {
        widths = widths.zipAll(l.map(_.size), 0, 0) .map(t => math.max(t._1,t._2))
      }
    }
    widths
  }

  def pad(cells: Seq[Seq[String]], widths: Seq[Int]): Seq[Seq[String]] = {
    for (line <- cells) yield
      for ((str, len) <- (line zip widths)) yield
        str.padTo(len, ' ')
  }
  
  def printMatrix(cells: Seq[Seq[String]], sep: String,
                  lineSep: String = System.getProperty("line.separator")): String =
    cells.map(_.mkString(sep).trim).mkString(lineSep)
}
