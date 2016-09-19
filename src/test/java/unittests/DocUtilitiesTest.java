package unittests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import paysafe.interns.helpers.DocUtilities;
import paysafe.interns.models.Doc;
import paysafe.interns.models.Project;

import java.util.HashSet;
import java.util.Set;

public class DocUtilitiesTest {
    private Project project;
    private Set<Doc> docs;
    private Doc doc;
    private String testedDocName;

    @BeforeClass
    public void setUp(){
        project = new Project();
        docs = new HashSet<>();
        doc = new Doc();
        testedDocName = "testDoc";
        doc.setName(testedDocName);
        docs.add(doc);
        project.setFiles(docs);

    }

    @Test
    public void getDocFromProjectByNameReturnsCorrectDoc(){
        Doc testedDoc = DocUtilities.getDocFromProjectByName(testedDocName, project);

        Assert.assertNotNull(testedDoc);
        Assert.assertTrue(testedDoc.getName().equals(doc.getName()));
    }

    @Test
    public void getDocFromProjectByNameWithIncorrectInputReturnsNull(){
        Doc testedDoc = DocUtilities.getDocFromProjectByName("wrong name", project);
        Assert.assertNull(testedDoc);
    }

    @Test
    public void docExistsInProject(){
        Assert.assertTrue(DocUtilities.docExistsInProject(doc, project));
    }
    @Test
    public void docDoesNotExistInProject(){
        Doc nonExistentDoc = new Doc();
        nonExistentDoc.setName("Non Existent Doc");
        Assert.assertFalse(DocUtilities.docExistsInProject(nonExistentDoc, project));
    }

    @Test
    public void docContentTypeJpegIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed("image/jpeg"));
    }

    @Test
    public void docContentTypePngIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed("image/png"));
    }

    @Test
    public void docContentTypeTextIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed("text/plain"));
    }

    @Test
    public void docContentTypeDocxIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    @Test
    public void docContentTypeDocIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed("application/msword"));
    }

    @Test
    public void docContentTypePdfIsAllowed(){
        Assert.assertTrue(DocUtilities.docContentTypeIsAllowed("application/pdf"));
    }

    @Test
    public void docWithNoContentTypeIsNotAllowed(){
        Assert.assertFalse(DocUtilities.docContentTypeIsAllowed(""));
    }

    @Test
    public void totalSizeInKbOfDocsInProjectReturnsCorrectSize(){
        long size = 0;
        for (Doc doc : project.getFiles()){
            size+= doc.getFileSizeKb();
        }
        Assert.assertEquals(size, DocUtilities.totalSizeInKbOfDocsInProject(project));
    }

}
