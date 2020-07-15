package org.nuxeo.labs;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.io.marshallers.json.document.DocumentModelJsonWriter;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriterTest;
import org.nuxeo.ecm.core.io.marshallers.json.JsonAssert;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext.CtxBuilder;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import java.io.Serializable;

@RunWith(FeaturesRunner.class)
@Features({PlatformFeature.class})
@Deploy({"org.nuxeo.labs.nuxeo-labs-drive-document-enricher-core"})
public class DriveEnricherTest extends AbstractJsonWriterTest.Local<DocumentModelJsonWriter, DocumentModel> {

    public DriveEnricherTest() {
        super(DocumentModelJsonWriter.class, DocumentModel.class);
    }

    @Inject
    private CoreSession session;

    @Test
    public void testWithoutBlob() throws Exception {
        DocumentModel obj = session.getDocument(new PathRef("/"));
        JsonAssert json = jsonAssert(obj, CtxBuilder.enrich("document", DriveEnricher.NAME).get());
        json = json.has("contextParameters").isObject();
        json.properties(1);
        json.has(DriveEnricher.NAME).isNull();
    }

    @Test
    public void testWithBlob() throws Exception {
        DocumentModel obj = session.createDocumentModel("/","File","File");
        Blob blob = new StringBlob("this is a test");
        blob.setFilename("testfile");
        obj.setPropertyValue("file:content", (Serializable) blob);
        obj = session.createDocument(obj);
        JsonAssert json = jsonAssert(obj, CtxBuilder.enrich("document", DriveEnricher.NAME).get());
        json = json.has("contextParameters").isObject();
        json.properties(1);
        System.out.println("link: " + json.has(DriveEnricher.NAME).toString());
        json.has(DriveEnricher.NAME).isText();
    }
}
