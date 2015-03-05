import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.likeyichu.doc.Doc;

public class DocTest {

	@Test
	public void test() {
		// List<Doc> docList=Doc.generateDocs();
		assertNotEquals(Doc.generateDocs(),null);
	}

}
