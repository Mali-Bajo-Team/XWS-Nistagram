using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Neo4jClient;
using RecommendationService.Model;

namespace RecommendationService.Repository.FollowRepo
{
    public class FollowRepository : IFollowRepository
    {
        private readonly IGraphClient _graphClient;

        public FollowRepository(IGraphClient graphClient)
        {
            _graphClient = graphClient;
        }

        public void Follow(string sourceUserId, string destinationUserId)
        {
            _graphClient.Cypher
                .Match("(sourceUser:User { Id: '" + sourceUserId + "' }), (destinationUser:User {Id: '" + destinationUserId + "'})")
                .Create("(sourceUser)-[:FOLLOW]->(destinationUser)").ExecuteWithoutResultsAsync();
        }

        public void UnFollow(string sourceUserId, string destinationUserId)
        {
            _graphClient.Cypher
                .Match("(:User { Id: '" + sourceUserId + "' })-[relationship:FOLLOW]->(:User {Id: '" + destinationUserId + "'})")
                .Delete("relationship")
                .ExecuteWithoutResultsAsync();
        }
    }
}
