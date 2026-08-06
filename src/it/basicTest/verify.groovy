import java.nio.file.Path
import java.nio.file.Files

def htmlFile = Path.of(basedir.toString(), "target", "site", "open-test-report.html")
println(htmlFile)
if (!Files.exists(htmlFile)) {
	throw new FileNotFoundException("Could not find " + htmlFile)
}

return true;
