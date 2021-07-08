using RecommendationService.Repository.FollowRepo;

namespace RecommendationService.Service.FollowServices
{
    public class FollowService : IFollowService
    {
        private readonly IFollowRepository _followRepository;

        public FollowService(IFollowRepository followRepository)
        {
            _followRepository = followRepository;
        }
        public void Follow(string sourceUserId, string destinationUserId)
        {
            _followRepository.Follow(sourceUserId,destinationUserId);
        }

        public void UnFollow(string sourceUserId, string destinationUserId)
        {
            _followRepository.UnFollow(sourceUserId,destinationUserId);
        }

        public void Block(string sourceUserId, string destinationUserId)
        {
            _followRepository.Block(sourceUserId, destinationUserId);
        }

        public void UnBlock(string sourceUserId, string destinationUserId)
        {
            _followRepository.UnBlock(sourceUserId, destinationUserId);

        }
    }
}
