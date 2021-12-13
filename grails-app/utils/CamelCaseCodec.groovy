class CamelCaseCodec {
    static encode = {target ->
        target.replaceAll(" ", "_")
    }

    static decode = {target ->
        String result = target.substring(0, 1);
        target.substring(1, target.size()).each {
            if (it.toUpperCase().equals(it)) {
                result = result + " " + it.toLowerCase()
            } else {
                result = result + it
            }
        }

        return result?.tokenize(" ")?.collect{
            List chars = it?.toLowerCase()?.toList()
        chars[0] = chars[0]?.toUpperCase()
        it = chars.join("")
        }?.join(" ") + " "
    }

}
