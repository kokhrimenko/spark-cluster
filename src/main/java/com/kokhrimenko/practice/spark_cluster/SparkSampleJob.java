package com.kokhrimenko.practice.spark_cluster;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;

public class SparkSampleJob {
    public static void main(String[] args) {
    	final SparkSession sparkSession = getNewSession();
        
        UserDefinedFunction custonUdf = functions.udf((Double val) -> val.intValue(), DataTypes.IntegerType);

        sparkSession.udf().register("custom_udf", custonUdf);
        Dataset<Row> dataset = sparkSession.read().parquet("/opt/spark-data/<!-- reald dataset in parquet format here-->")		// real dataset in parquet format here
				.repartition(100, functions.col("<!-- real column name from the dataset-->"));									// replace me with the real column name

        dataset.createOrReplaceTempView("data");

        dataset.sqlContext().sql(
                "select custom_udf(<!-- real column name from the dataset-->) as custom_udf_value, <!-- real column name from the dataset--> as orig_value"
                + " from data"
                + " order by <!-- real column name from the dataset-->")
            .show();
    }

    private static SparkSession getNewSession() {
        SparkConf conf = new SparkConf();

        conf.set("spark.sql.datetime.java8API.enabled", "true");
        conf.set("spark.sql.adaptive.enabled", "true");
        conf.set("spark.sql.adaptive.coalescePartitions.enabled", "true");
        conf.set("spark.deploy.spreadOut", "true");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        
        return SparkSession.builder().config(conf).getOrCreate();
}
    
}
