package org.siemac.metamac.common.metadata.web.server;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.web.common.server.servlet.FileDownloadServletBase;
import org.siemac.metamac.web.common.shared.utils.SharedTokens;

public class FileDownloadServlet extends FileDownloadServletBase {

    /**
     * 
     */
    private static final long                  serialVersionUID = 6267996624034326676L;
    private CommonMetadataConfigurationService configurationService;

    @Override
    protected File getFileToDownload(HttpServletRequest request) throws Exception {
        if (!StringUtils.isEmpty(request.getParameter(SharedTokens.PARAM_DOC))) {
            return getFileFromDocs(request.getParameter(SharedTokens.PARAM_DOC));
        } else if (!StringUtils.isEmpty(request.getParameter(SharedTokens.PARAM_FILE_NAME))) {
            return getFileFromTempDir(request.getParameter(SharedTokens.PARAM_FILE_NAME));
        } else {
            throw new InvalidArgumentException("You must specify some action");
        }

    }

    private File getFileFromDocs(String fileName) throws Exception {
        checkValidFileName(fileName);
        String docsPath = getConfigurationService().retrieveDocsPath();
        return new File(docsPath + File.separatorChar + fileName);
    }

    private CommonMetadataConfigurationService getConfigurationService() {
        if (configurationService == null) {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(CommonMetadataConfigurationService.class);
        }
        return configurationService;
    }

}
