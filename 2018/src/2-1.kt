//
// checksum is 5658
//

fun main(args: Array<String>) {
    val result = input.map(::extract).fold(Result(0, 0), ::addPartial)
    println("checksum is ${result.sumTwos * result.sumThrees}")
}

private data class Partial(val two: Boolean, val three: Boolean)

private data class Result(val sumTwos: Int, val sumThrees: Int)

private fun addPartial(result: Result, partial: Partial) =
    Result(
        result.sumTwos + (if (partial.two) 1 else 0),
        result.sumThrees + (if (partial.three) 1 else 0)
    )


private fun extract(id: String): Partial {
    val counts = id.asSequence().fold(mutableMapOf<Char, Int>(), ::putOrIncrement).values
    return Partial(counts.contains(2), counts.contains(3))
}

private fun putOrIncrement(acc: MutableMap<Char, Int>, c: Char): MutableMap<Char, Int> {
    acc.put(c, acc.getOrDefault(c, 0) + 1)
    return acc
}


private val input = arrayOf(
"xdmgyjkpruszabaqwficevtjeo",
"xdmgybkgwuszlbaqwfichvtneo",
"xdmgyjkpruszlbcwwfichvtndo",
"xdmgcjkprusyibaqwfichvtneo",
"xdmgyjktruszlbwqwficuvtneo",
"xdmgxjkpruszlbaqyfichvtnvo",
"xdmgytkpruszlbaqwficuvtnlo",
"xdmgydkpruszlbaqwfijhvtnjo",
"xfmgyjkmruszlbaqwfichvtnes",
"xdmgyrktruszlraqwfichvtneo",
"xdmgyjkihuszlbaqdfichvtneo",
"hdmgyjkpruszeiaqwfichvtneo",
"xdmzyjkpruszlbaqwgichvtnxo",
"xdmgyjknquszlbpqwfichvtneo",
"idmgyjrpruszlbtqwfichvtneo",
"xkmgyjkpruuzlbaqwfichvfneo",
"xdmgyjkpruszlfaqwficnvtner",
"xdmgyjkpruszlbpqwficwvteeo",
"xdmgyjkpwuszlbiqwfhchvtneo",
"xdmgyjkpruszwbaqwfichrtnbo",
"xdpgyjkprusblbaqwfgchvtneo",
"xdmryjkcruszlbaqwfichvtnee",
"xwmgylkpruszlbaqwfcchvtneo",
"xdmgyjkpruszflaqwfixhvtneo",
"xdmgyjkmruszloaqwfichvteeo",
"xvmgrjkpruszlbaqwfichvsneo",
"xdmvyjkprusmlbaqwfichvtnes",
"xdmgyjkpruszlbaqwfichkgbeo",
"xdmgyikpruxzlbaqwfichvtnei",
"xdmgyjkprugzlbaqhfichvtveo",
"xdmgyjkpruszlbaqjaichftneo",
"xdmzijkpruszlbaqwwichvtneo",
"xdmgyjkprsszlbaqwfihhvlneo",
"xdmgyjkprusqlwaqzfichvtneo",
"ximgyjkpruszlbawwfichvtnen",
"xsmgyjzpruszlbaqwfichvaneo",
"xdmgyjkpruszlcaoyfichvtneo",
"xdmgyjkprusmlbaqvnichvtneo",
"xdmgyjkvruszmbaqwfichvtueo",
"xdmgyjppuuszleaqwfichvtneo",
"xddgyjkprubzlbaqwfichvaneo",
"xdmgwjkpruszebaswfichvtneo",
"xdogyjkpruszlblqwfichvdneo",
"xdkgyjgpruszlbaqwfizhvtneo",
"xdvgyjkpruszlbdqwfichvtqeo",
"xdmgyjlpruszlbapwficgvtneo",
"xdmgyjkpruszlbaqofickvtngo",
"xdmgyjkprqszliaywfichvtneo",
"xdqgyjkpruszlbcqwficnvtneo",
"xdmgdjkpruszlbaqwxichvtseo",
"xdmgyjkpruczlbaqwfichdtnfo",
"xdmgyjkpruszluaqwficzvtnjo",
"xdmgyjkproszlbaqwfacevtneo",
"xfmgijkpruszlbrqwfichvtneo",
"odmgyjkpluszlbaqwfichvuneo",
"xdmgyjkpruszlbaqwwichukneo",
"xdmgdjkpruszwbaqwfichvtnet",
"xdmgyjkzrusvlbaqwrichvtneo",
"xdmgylkprutzlbaqwfichvtnbo",
"xdmgyjkpruszsbaqwfijtvtneo",
"xdmgyjkproszlbjqwfichntneo",
"xdmgyhkpluszlbaqwfichvtnlo",
"xdmgyjhprushlbaqwfichvtnzo",
"gdmoyjkpruszlbarwfichvtneo",
"cdmgyjkpruszlbaqwfcchvtned",
"xgmgyjkpruszlbaqwfschvtnek",
"xdmgyjkprusnlzamwfichvtneo",
"xdmgyjkprgszlbaxwfichvuneo",
"txmgyjksruszlbaqwfichvtneo",
"xdmgyjkprusbbbpqwfichvtneo",
"xdmoyjkpruszlbaqwfighvtxeo",
"xdmgyjkpruslhbaqwfichptneo",
"xdmgzjkpruszlbaqwffcmvtneo",
"xdmgyjkiruszlbaqgficuvtneo",
"vdbgyjkpruszlbaqwfichvtnek",
"xdmgyjspruszlbaqwfochvtney",
"xdmgyjkpruszibaqwfivhvteeo",
"xdmgyjkpruszfbaqwficbvtgeo",
"xdmgyjkprystlbaqwxichvtneo",
"xdmfyjkpryszlxaqwfichvtneo",
"xdmgyjgpruspybaqwfichvtneo",
"xdmgyjklruszlbjqwdichvtneo",
"xdmgyjkzruszltaqwfichvtnek",
"xdmgqjkpruszlzaqwfichvtneh",
"xdmgyjhnruszlbaqwficqvtneo",
"xdmgyjkproszlbaqweichvtnez",
"xdmgyjkprurzlbaawfichytneo",
"xdmgyfkpruszlbaqwfschutneo",
"xdmnyjkpruszlbaawjichvtneo",
"xdmgyjkpybszlbaqwfichvwneo",
"xdmgtjkhruszlbaqwfichatneo",
"xamgyjkprurzlbaqwfichvaneo",
"xdmgyjkpruszlbaqwgichvtnqv",
"ndmgyjkpruszlsaqwfuchvtneo",
"xdmgygkpgusrlbaqwfichvtneo",
"xdmgyjkpruszfbaqwfichvtnmy",
"xdmgyjkprupflbaqwfichvjneo",
"ndmgyjkpruszlbagwfichvtnxo",
"xdmgyjkpruszlbafwfilhvcneo",
"xdmgyjkpruszlbaqwfichvjsea",
"xebgyjkpruszlbaqafichvtneo",
"xdmkyjdpruszlbaqwfichvtnei",
"xomgyjkprufzlbaqwfochvtneo",
"xdmgyjkprfsllbaqwfiihvtneo",
"xdmyyjkpruszebaqwficmvtneo",
"xdmnyjkpruczlbarwfichvtneo",
"xdmgyjkpruszcbaqwbichvtneg",
"xdmgxjkpluszlbapwfichvtneo",
"xgrlyjkpruszlbaqwfichvtneo",
"xdmgyjkpruszlraqwxcchvtneo",
"xdmhyjupruszlbaqafichvtneo",
"xdmgnjkpruszlbkqwfjchvtneo",
"xdmgyjkpruszlwaqwfichvtndg",
"xdmgfjkpruvqlbaqwfichvtneo",
"xdmgejkptuszlbdqwfichvtneo",
"xlmgyjkpruszlnaqwfochvtneo",
"xdmgcjkpruszlbaqwfiqhvaneo",
"xdmgyjupruyzlbaywfichvtneo",
"gdmgyjkpruyzlbaqwficevtneo",
"xdmgyjkaruazlbapwfichvtneo",
"xsmiyjkpruszlbaqwfichvtveo",
"xdmiyjkprukzlbaqwfichvtnea",
"xdbgmjkxruszlbaqwfichvtneo",
"xdmgyjkpruskvbaqwfichdtneo",
"xdmgyjkprusznbaqwficshtneo",
"xdmgyjkprusrlbaqwfzchetneo",
"xdmgyrkpruszzbaqwfichvtned",
"xdmgyjkprusolbacwmichvtneo",
"xdmgypkpruszlbaqwfichvtmgo",
"xdmgyjkprumzlbhqwfichttneo",
"xdmgydkprusglbaqwfichvtnei",
"xdmuyjkpruszlbpqwfichvtyeo",
"xdmtymkprusslbaqwfichvtneo",
"xdmgyjjprkszlbaqwfqchvtneo",
"xdmgvjdpruszlbaqwfichgtneo",
"xdtgyjkpruwzlbaqwfjchvtneo",
"xdmgyjkpruszlbafseichvtneo",
"xdmgvjkpruszlraawfichvtneo",
"xdmgyukprgszlbatwfichvtneo",
"xhmgyjkpruszliaqwnichvtneo",
"xdmgyjspruszlbwqyfichvtneo",
"xdmgyjkjruszlqaqwfichvtnvo",
"xdmgyjkiruszlbnqwfichmtneo",
"ximgyjkpruszlbaqwfvcevtneo",
"xdmdyjkpruszlbaqwsithvtneo",
"ndmgyjkpruszlbaqwfilhatneo",
"xdmgyjkpruszlbaqwfinhvcnez",
"xdmgypkpsuszlbajwfichvtneo",
"xdpgmjkpluszlbaqwfichvtneo",
"xdmgyjnprupzlbaqwfichvtnel",
"xbmgyjkprmszlfaqwfichvtneo",
"xdmgyjkpausllbaqwfichvtseo",
"xdmgyjkpruszlbaqwfqchttnes",
"xgmgyjkpruszlbaxwfichvtneb",
"xdmgyjkpruszabqqwfichvineo",
"xdmgpjkpquszlbaqwfichvdneo",
"xdmgyjkeruszlbaqdficbvtneo",
"xdmaujkpruszlbaqwfichvteeo",
"xdmgyjkpruszlbaqwrirhvtnev",
"xdmgyjkpsugzllaqwfichvtneo",
"xdmgyjkpruszlbaqwfichctnlm",
"xdmeyjkpruszlbacwfiwhvtneo",
"xdmgyjkpiuhzlbaqwfijhvtneo",
"xdmgyjkpruszlbmqhfiohvtneo",
"xdegyjkpbuszlbbqwfichvtneo",
"xdmggxkpruszlbaqwfirhvtneo",
"xdmgojkpruszlbaqvfichvtteo",
"xdmgyjhtruszlbaqwmichvtneo",
"rdmgyjkpruszlbaqwfichvthek",
"xdlgyjqpruszlbaqwfbchvtneo",
"xdmgyjspriszlbavwfichvtneo",
"rdkgyjkpruszlbaqwfichvtnuo",
"tdmgyjkuruszlbaqwfichvtnev",
"xdmgyjkpxuszlbaqwfkchvtnso",
"xdegyjkpruszlbbqxfichvtneo",
"xdmgyjkpruszlbaqwficpvtket",
"xdmgyjkpruszliaqwfnchvtnec",
"xdmgyjkpreszlbaqwficdvtdeo",
"rdmgyjkpruszlbaywfychvtneo",
"xdmgywkpruszlbaqwficrvtaeo",
"xdmgyjkpruszlbanwflchvoneo",
"xdmgyjkpruyzlbaqufychvtneo",
"symgyjkpruszlbaqwfichvtqeo",
"xdmgyjkpruszlbaqwfichvbzqo",
"xzfgyjkpruszlbaqwfichvtveo",
"udmgyjepruszlbaqwfichbtneo",
"xhmgyjkpruszlbaqwfjchvtnef",
"xdhgyjkpruszlbaqaftchvtneo",
"xdmzyjkjruszlbaqwfichvtnwo",
"xdmgyjepruszlbaqwffchvtnef",
"xdmgyjkprurzlbaqwfikhvtneq",
"xomoyjkpruszkbaqwfichvtneo",
"xdmgyjkpiuszubaqwfichktneo",
"xdmgyjkprusdlbaqwhihhvtneo",
"xdmgyjkpruszlbaqwwirhvxneo",
"xdmgyjkpruszlbaqwficgitzeo",
"xdmgyjlpruszlbaqwfichpjneo",
"xjmgyjkpxuszlbaqwfichatneo",
"xdmgylkpruszlbaqwfiehvtnez",
"xdmgbjkpruszmbaqwfihhvtneo",
"xdmgyjkprubzlwaqwfichvtxeo",
"xdmgyjhlrustlbaqwfichvtneo",
"xdmmyjkpruszlbaqwfdchitneo",
"xdmgyjkpruszlbaqwoichhtbeo",
"xdzgyjkprvszlcaqwfichvtneo",
"ndmgyjkpruszlbaqwficavxneo",
"xdmgyjfpruszxbaqzfichvtneo",
"xdmgyjkpeuszlbaqzficdvtneo",
"xdmgyjkpruszlbmqffidhvtneo",
"xdnvyjkpruszlbafwfichvtneo",
"xdygyjkpruszlbljwfichvtneo",
"xdigyjkpruszlbeqwfuchvtneo",
"xdmgyjkpruszlbpzwfichvteeo",
"bdmgyjbpruszldaqwfichvtneo",
"xdmgyjkprrszlbaqmpichvtneo",
"idmgyjkpruszlbaqyfichvtkeo",
"xdmgyjkmrqsclbaqwfichvtneo",
"xdmgyjkpruazlbeqwfichvtxeo",
"ddmgyjkpruszlbsqwfichotneo",
"xdmgyqkpruszjbaqwfxchvtneo",
"xdmnyjkpruozlbaqwfichvtreo",
"edmgyjkpruszlbuqwwichvtneo",
"xdmgyjkprmshlbaqwfichctneo",
"xdmgyjkpruszlbaqwffghotneo",
"xdmcyjkprfszlbaqnfichvtneo",
"xdmgyjypruszhbaqwficyvtneo",
"xdmgyjkprzszlyaqwficmvtneo",
"xlmgyjkprzszlbaqwficyvtneo",
"xdmgyjkprutulbaqwfithvtneo",
"xdygyjkpruszlbpqwfichvpneo",
"xdmgsjkpoumzlbaqwfichvtneo",
"xdmgyjkpyuszlbaqdfnchvtneo",
"xdxgyjkpruszlbaqwfizhvtnjo",
"xdmgyjkpruszlbaqwfschvkndo",
"xdmgpjkprnszlcaqwfichvtneo",
"xhmgyjkpruszlbaqwficgvtnet",
"xdmgyjkpruswlbaqwfichvtqer",
"ddmgyjkprcszlbaqwfichqtneo",
"xdmgyjkpruhhlbaqwpichvtneo",
"xdmgyjkeraszlbaqwfichvtnso",
"nomgyjkpruszlbaqwficavxneo",
"xdmgyjkprdszlbaqwfobhvtneo",
"xdmgyjkprgszlbaqwfichvtdao",
"xomgyjspruswlbaqwfichvtneo",
"xdzgyjkpruszlbaqwficwvpneo",
"admgejkpruszlbaqwfimhvtneo",
"xdtgyjkpruszlmaqwfiqhvtneo",
"xdmgymkprusqlbaqwtichvtneo",
"xdmgyjkpluszlbaqwfidhvtnea",
"ztmgyjjpruszlbaqwfichvtneo"
)
