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
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity.ItemItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class UserBasedRecommenderEvaluator {

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
			
			// 사용자 기반 추천기 - PearsonCorrelationSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
			
			// 사용자 기반 추천기 - EuclideanDistanceSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
			
			// 사용자 기반 추천기 - LogLikelihoodSimilarity - 최근접 이웃 2명
			@Override public Recommender buildRecommender(DataModel model) throws TasteException { 
				UserSimilarity similarity = new	LogLikelihoodSimilarity(model); 
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model); 
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			}
			
			// 사용자 기반 추천기 - CityBlockSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new CityBlockSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
			
			// 사용자 기반 추천기 - TanimotoCoefficientSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
			
			// 사용자 기반 추천기 - UncenteredCosineSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new UncenteredCosineSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
			
			// 사용자 기반 추천기 - SpearmanCorrelationSimilarity - 최근접 이웃 2명
//			@Override
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				UserSimilarity similarity = new SpearmanCorrelationSimilarity(model);
//				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
//				return new GenericUserBasedRecommender(model, neighborhood, similarity);
//			}
		};
		
		// 5%의 데이터에서 70%를 학습에 사용, 30%는 테스트에 사용
		double AverageScore = AverageEvaluator.evaluate(builder, null, model, 0.7, 0.1);
		double RMSEScore = RMSEevaluator.evaluate(builder, null, model, 0.7, 0.1);

		System.out.println("Average : " + AverageScore);
		System.out.println("RMSE : " + RMSEScore);

		long end = System.currentTimeMillis();
		System.out.println("실행 시간 : " + (end - start) / 1000.0);
		System.out.println("프로그램 실행 종료");

	}
}
