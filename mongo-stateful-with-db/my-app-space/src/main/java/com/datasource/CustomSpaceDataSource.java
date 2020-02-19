/*
package com.datasource;

import com.gigaspaces.datasource.DataIterator;
import com.gigaspaces.datasource.SpaceDataSource;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.persistency.support.ConcurrentMultiDataIterator;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class CustomSpaceDataSource extends SpaceDataSource {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ClusterInfoContext
    private ClusterInfo clusterInfo;
    private int multiDataIteratorThreadPoolSize = 1;

    @SuppressWarnings("unchecked")
    @Override
    public DataIterator<Object> initialDataLoad() {
ResourceBundle faresAndRulesProperties = ResourceBundle.getBundle("faresAndRules");
        String space_recovery = faresAndRulesProperties.getString("space_recovery");
        if(space_recovery == null || space_recovery.equals("false")){
            logger.info("Space Recovery is not enabled.");
            return null;
        }


        logger.info("Space Recovery Started ...");
        ConcurrentMultiDataIterator concurrentIterator = null;
        DataIterator<?> iterators[] = null;
        try {
            List<DataIterator> dataIteratorList = new ArrayList<DataIterator>();
            int count = 1;

            final int instanceId = clusterInfo.getInstanceId();
            COSUtil cosObj = COSUtil.getInstance();
            if(!cosObj.isBucketAvailable(cosObj.getNewBucketName())){
                logger.info("Space Recovery Fails as bucket with name "+cosObj.getNewBucketName()+"  is not available.");
                return concurrentIterator;
            }
            int count = 1;
            List<String> s3ObjectNames = cosObj.getObjects(cosObj.getNewBucketName(), "partition-" + instanceId + "/");
            if(s3ObjectNames.isEmpty()){
                logger.info("Space Recovery Fails as there is no files in bucket "+cosObj.getNewBucketName()+" and folder name "+ "partition-" + instanceId);
                return concurrentIterator;
            }

            // Sort the s3objectNames as per the suffix numberic count
Collections.sort(s3ObjectNames, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return extractInt(o2) - extractInt(o1);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            for (String objName : s3ObjectNames) {

                DataIterator da = new CustomSpaceDataIterator("Employee", count);
            //    count++;
                dataIteratorList.add(da);
  //          }
            concurrentIterator = new ConcurrentMultiDataIterator(dataIteratorList.toArray(new DataIterator[dataIteratorList.size()]), multiDataIteratorThreadPoolSize);
        } catch (final Exception e) {
            logger.error("Error Creating DataIterator:" + e);
            e.printStackTrace();
        }
        return concurrentIterator;
    }
}
*/
