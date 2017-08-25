package recommend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class UserRecommendTutorial {
	public static void main(String[] args) {
		/**
		 *  데이터 전처리 작업 필요
		 */
		
		try {
			
			long start = System.currentTimeMillis();
			System.out.println("프로그램 실행 시작");

			/** 
			 *  데이터 모델 생성 
			 *  - 입력 데이터 규칙 : 사용자ID(정수), 아이템ID(정수), 선호수치(큰 값일수록 선호도가 높아야함)
			 *  - 반환값 : DataModel
			 */
			DataModel dm = new FileDataModel(new File("data/tripAdvisor/ratings/part-00000-bab670ee-199b-4b0f-b6b4-c9b787ce9bd2-c000.csv"));
			
			/**
			 *  사용자 유사도 모델 생성
			 *  1. 피어슨 상관관계 알고리즘	 	: PearsonCorrelationSimilarity
			 *  2. 유클리드 거리 알고리즘 		: EuclideanDistanceSimilarity
			 *  3. 스피어만 상관관계 알고리즘 	: SpearmanCorrelationSimilarity
			 *  - 입력값 : DataModel, Weighting.WEIGHTED(가중치)
			 *  - 반환값 : UserSimilarity(사용자간의 유사도 개념을 캡슐화한 클래스)
			 */
			UserSimilarity sim = new PearsonCorrelationSimilarity(dm);
			
			/**
			 * 아이템 유사도 모델 생성
			 */
			
			
			/**
			 *  유사한 이웃 그룹 생성
			 *  1. 고정 크기 이웃	: NearestNUserNeighborhood
			 * 		- 입력값 : 이웃의수, UserSimilarity, DataModel
			 *  	- 반환값 : UserNeighborhood(가장 유사한 사용자 그룹의 개념을 캡슐화한 클래스)
			 *  2. 임계치 기반 이웃	: ThresholdUserNeighborhood
			 * 		- 입력값 : 임계치, UserSimilarity, DataModel
			 *  	- 반환값 : UserNeighborhood(가장 유사한 사용자 그룹의 개념을 캡슐화한 클래스)
			 */
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, sim, dm);
			
			/**
			 *  추천기 생성
			 *  1. 사용자 기반 추천기 : GenericUserBasedRecommender
			 *  - 입력값 : DataModel, UserNeighborhood, UserSimilarity
			 *  - 반환값 : UserBasedRecommender
			 *  2. 아이템 기반 추천기 : GenericItemBasedRecommender
			 *  - 입력값 : DataModel, ItemSimilarity
			 *  - 반환값 : ItemBasedRecommender
			 */
			UserBasedRecommender recommender = new GenericUserBasedRecommender(dm, neighborhood, sim);
			
			/**
			 *  생성된 추천기 캐싱
			 *  - 입력값 : UserBasedRecommender
			 *  - 반환값 : Recommender
			 */
//			Recommender cachingRecommender = new CachingRecommender(recommender);
			
			/*
			int x = 1;
//			List<Map<String, Object>> similarList = new ArrayList<Map<String, Object>>();
			// 데이터 모델 내의 유저들의 iterator를 단계별로 이동하며 추천 아이템들 제공
			for(LongPrimitiveIterator users = dm.getUserIDs(); users.hasNext();) {
				
				// 유저 ID
				long userID = users.nextLong();
				System.out.println("일련번호 : " + x + ", " + "userID : " + userID);
				
				// 유사도 : -1 ~ 1
				double similarity = sim.userSimilarity(1, userID);
				Map<String, Object> similarMap = new HashMap<String, Object>();
				similarMap.put("userID", userID);
				similarMap.put("similarity", similarity);
				
				similarList.add(similarMap);
				
				// 현재 유저 ID에 해당되는 5개 아이템 추천
				List<RecommendedItem> recommendations = recommender.recommend(userID, 3);
				for (RecommendedItem recommenation : recommendations) {
					System.out.println("유저ID : " + userID + ", " + "호텔ID : " + recommenation.getItemID() + ", " + "추천지수 : " + recommenation.getValue());
				}
				
				if(++x > 5) break;
			}
			*/
			
			
			// 유저 목록
			/*
			for(LongPrimitiveIterator users = dm.getUserIDs(); users.hasNext();) {
				System.out.println(users.nextLong() + ", ");
			}
			*/
			
			// 현재 유저 ID에 해당되는 5개 아이템 추천
			long userID = 1;	// linda b
			int recCnt = 5;
			List<RecommendedItem> recommendations = recommender.recommend(userID, recCnt);
			for (RecommendedItem recommenation : recommendations) {
				System.out.println("유저ID : " + userID + ", " + "호텔ID : " + recommenation.getItemID() + ", " + "추천지수 : " + recommenation.getValue());
			}
			
			
			/*
			Collections.sort(similarList, new Comparator<Map<String, Object>>() {
	            @Override
	            public int compare(Map<String, Object> first, Map<String, Object> second) {
	                return Double.compare((double)second.get("similarity"), (double)first.get("similarity"));
	            }
	        });
	        */
			
			long end = System.currentTimeMillis();
			System.out.println( "실행 시간 : " + ( end - start )/1000.0 );

			
		} catch (IOException e) {
			System.out.println("There was an IO Exception");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("There was a Taste Exception");
			e.printStackTrace();
		}
		
	}

}
