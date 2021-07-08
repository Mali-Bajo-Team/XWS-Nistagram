using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Neo4jClient;
using RecommendationService.DTOs;


namespace RecommendationService.Repository.RecommendationRepo
{
    public class RecommendationRepository : IRecommendationRepository
    {
        private readonly IGraphClient _graphClient;

        public RecommendationRepository(IGraphClient graphClient)
        {
            _graphClient = graphClient;
        }

        public async Task<List<RecommendationDTO>> GetUserRecommendations(string sourceUserId)
        {
            var result = await _graphClient.Cypher
                .Match("(sourceUser:User {Id:'" + sourceUserId + "'})-[:FOLLOW]->(destinationUser)-[:FOLLOW]->(destinationUserFriend)")
                .Where("NOT(sourceUser) - [] - (destinationUserFriend) ")
                .With("destinationUserFriend.Id AS recommendedUserId, count(*) AS priority")
                .Return((recommendedUserId, priority) => new RecommendationDTO
                {
                    RecommendedId = recommendedUserId.As<string>(),
                    Priority = priority.As<int>()
                })
                .ResultsAsync;

            return result.OrderByDescending(user => user.Priority).ToList();
        }
    }
}
