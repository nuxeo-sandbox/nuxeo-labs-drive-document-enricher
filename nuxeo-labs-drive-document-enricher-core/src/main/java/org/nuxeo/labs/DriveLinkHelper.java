/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Michael Vachette
 */

package org.nuxeo.labs;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.model.PropertyNotFoundException;
import org.nuxeo.ecm.core.io.download.DownloadService;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext;
import org.nuxeo.runtime.api.Framework;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DriveLinkHelper {

    public static String getDriveLink(DocumentModel doc, RenderingContext ctx) {

        Blob blob;
        try {
            blob = (Blob) doc.getPropertyValue("file:content");
        } catch (PropertyNotFoundException e) {
            return null;
        }

        if (blob == null || blob.getFilename() == null) {
            return null;
        }

        try (RenderingContext.SessionWrapper wrapper = ctx.getSession(doc)) {
            StringBuffer link = new StringBuffer();
            link.append("nxdrive://edit/")
                    .append(ctx.getBaseUrl().replaceAll("://", "/"))
                    .append("/user/")
                    .append(wrapper.getSession().getPrincipal().getName())
                    .append("/repo/")
                    .append(doc.getRepositoryName())
                    .append("/nxdocid/")
                    .append(doc.getId())
                    .append("/filename/")
                    .append(URLEncoder.encode(blob.getFilename(), StandardCharsets.UTF_8.toString()))
                    .append("/downloadUrl/");
            DownloadService downloadService = Framework.getService(DownloadService.class);
            String downloadUrl = downloadService.getDownloadUrl(doc,"file:content",blob.getFilename());
            downloadUrl = downloadUrl.substring(downloadUrl.indexOf("nxfile/"));
            link.append(downloadUrl);
            return link.toString();
        } catch (IOException e) {
            return null;
        }
    }


}
