using Neo4jClient;
using RecommendationService.Model;

namespace RecommendationService.Repository.UserRepo
{
    public class UserRepository : IUserRepository
    {
        private readonly IGraphClient _graphClient;

        public UserRepository(IGraphClient graphClient)
        {
            _graphClient = graphClient;
        }

        public User CreateUser(string userId)
        {
            User user = new User {Id = userId};

            _graphClient.Cypher
                .Create("(user:User $user)")
                .WithParam("user", user)
                .ExecuteWithoutResultsAsync()
                .Wait();

            return user;
        }
    }
}
