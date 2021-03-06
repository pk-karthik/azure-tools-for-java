/**
 * Copyright (c) Microsoft Corporation
 * <p/>
 * All rights reserved.
 * <p/>
 * MIT License
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.microsoft.azure.hdinsight.serverexplore;

import com.microsoft.azure.hdinsight.common.ClusterManagerEx;
import com.microsoft.azure.hdinsight.common.CommonConst;
import com.microsoft.azure.hdinsight.sdk.cluster.IClusterDetail;
import com.microsoft.azure.hdinsight.serverexplore.hdinsightnode.ClusterNode;
import com.microsoft.azure.hdinsight.serverexplore.hdinsightnode.HDInsightRootModule;
import com.microsoft.tooling.msservices.helpers.NotNull;
import com.microsoft.tooling.msservices.helpers.azure.AzureCmdException;
import com.microsoft.tooling.msservices.serviceexplorer.EventHelper;
import com.microsoft.tooling.msservices.serviceexplorer.Node;

import java.util.List;

public class HDInsightRootModuleImpl extends HDInsightRootModule {

    private static final String HDInsight_SERVICE_MODULE_ID = HDInsightRootModuleImpl.class.getName();
    private static final String ICON_PATH = CommonConst.HDExplorerIconPath;
    private static final String BASE_MODULE_NAME = "HDInsight";

    public HDInsightRootModuleImpl(@NotNull Node parent) {
        super(HDInsight_SERVICE_MODULE_ID, BASE_MODULE_NAME, parent, ICON_PATH);
    }

    private List<IClusterDetail> clusterDetailList;

    @Override
    public HDInsightRootModule getNewNode(@NotNull Node node) {
        return new HDInsightRootModuleImpl(node);
    }

    @Override
    protected void refresh(@NotNull EventHelper.EventStateHandle eventState) throws AzureCmdException {
        synchronized (this) { //todo???
            removeAllChildNodes();
//            TelemetryManager.postEvent(TelemetryCommon.HDInsightExplorerHDInsightNodeExpand, null, null);

            clusterDetailList = ClusterManagerEx.getInstance().getClusterDetails(getProject());

            if (clusterDetailList != null) {
                for (IClusterDetail clusterDetail : clusterDetailList) {
                    addChildNode(new ClusterNode(this, clusterDetail));
                }
            }
        }
    }

    @Override
    public void refreshWithoutAsync() {
        synchronized (this) {
            removeAllChildNodes();
            clusterDetailList = ClusterManagerEx.getInstance().getClusterDetailsWithoutAsync(getProject());

            if (clusterDetailList != null) {
                for (IClusterDetail clusterDetail : clusterDetailList) {
                    addChildNode(new ClusterNode(this, clusterDetail));
                }
            }
        }

    }

}