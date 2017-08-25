package recommend;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity.ItemItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class ItemBasedRecommenderEvaluator {

	public static void main(String[] args) throws IOException, TasteException {

		long start = System.currentTimeMillis();
		System.out.println("프로그램 실행 시작");

		// 반복되는 결과 생성
		RandomUtils.useTestSeed();

		DataModel model = new FileDataModel(new File("data/tripAdvisor/ratings/part-00000-bab670ee-199b-4b0f-b6b4-c9b787ce9bd2-c000.csv"));

		// AAD
		RecommenderEvaluator AverageEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();

		// RMSE
		RecommenderEvaluator RMSEevaluator = new RMSRecommenderEvaluator();

		// 추천기 빌더
		RecommenderBuilder builder = new RecommenderBuilder() {

			// 아이템 기반 추천기 - PearsonCorrelationSimilarity
			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
				ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
				return new GenericItemBasedRecommender(model, similarity);
			}
			
//			// 아이템 기반 추천기 - EuclideanDistanceSimilarity
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
//				return new GenericItemBasedRecommender(model, similarity);
//			}
			
			// 아이템 기반 추천기 - LogLikelihoodSimilarity
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
//				return new GenericItemBasedRecommender(model, similarity);
//			}
			
			// 아이템 기반 추천기 - CityBlockSimilarity
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new CityBlockSimilarity(model);
//				return new GenericItemBasedRecommender(model, similarity);
//			}
			
			// 아이템 기반 추천기 - TanimotoCoefficientSimilarity
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
//				return new GenericItemBasedRecommender(model, similarity);
//			}
			
			// 아이템 기반 추천기 - UncenteredCosineSimilarity
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
//				return new GenericItemBasedRecommender(model, similarity);
//			}
			
		};

		// 데이터에서 70%를 학습에 사용, 30%는 테스트에 사용(마지막 파라미터는 전체 데이터중에 사용할 비율)
		double AverageScore = AverageEvaluator.evaluate(builder, null, model, 0.7, 1.0);
		double RMSEScore = RMSEevaluator.evaluate(builder, null, model, 0.7, 1.0);

		System.out.println("Average : " + AverageScore);
		System.out.println("RMSE : " + RMSEScore);

		long end = System.currentTimeMillis();
		System.out.println("실행 시간 : " + (end - start) / 1000.0);
		System.out.println("프로그램 실행 종료");

	}
}
