using RecommendationService.Model;
using RecommendationService.Repository.UserRepo;

namespace RecommendationService.Service.UserServices
{
    public class UserService : IUserService
    {

        private readonly IUserRepository _userRepository;

        public UserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public User CreateUser(string userId)
        {
            return _userRepository.CreateUser(userId);
        }

        public void DeleteUser(string userId)
        {
           _userRepository.DeleteUser(userId);
        }
    }
}
