package org.nuxeo.labs;

import com.fasterxml.jackson.core.JsonGenerator;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

import java.io.IOException;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

/**
 * Enrich {@link nuxeo.ecm.core.api.DocumentModel} Json.
 * <p>
 * Format is:
 * </p>
 * <pre>
 * {@code
 * {
 *   ...
 *   "contextParameters": {
 *     "drive": { ... }
 *   }
 * }}
 * </pre>
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class DriveEnricher extends AbstractJsonEnricher<DocumentModel> {

    public static final String NAME = "drive";

    public DriveEnricher() {
        super(NAME);
    }

    @Override
    public void write(JsonGenerator jg, DocumentModel doc) throws IOException {
        String link = DriveLinkHelper.getDriveLink(doc,ctx);
        jg.writeFieldName(NAME);
        if (link  == null) {
            jg.writeNull();
        } else {
            jg.writeString(link);
        }
    }
}
