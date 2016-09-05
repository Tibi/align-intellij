package tibi.align

object AlignMain {

  def main(args: Array[String]) {
      unitTest()
  }

  val testStr = """auie,jldv,nrst
  uuuuuuuu,ddddddd,rrrrrrrrrrr
  "nrst,ldv", tdte, 'td,"ldt,ldt",nst'"""

  val alignedStr = """auie      , jldv   , nrst
uuuuuuuu  , ddddddd, rrrrrrrrrrr
"nrst,ldv", tdte   , 'td,"ldt,ldt",nst'"""

  def unitTest() {
    val res = Align.align(testStr)
    println(res)
    //println(res.size, alignedStr.size)
    assert(res == alignedStr)
  }
}