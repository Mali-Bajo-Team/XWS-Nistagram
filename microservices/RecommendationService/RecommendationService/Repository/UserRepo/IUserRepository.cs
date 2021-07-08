using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using RecommendationService.Model;

namespace RecommendationService.Repository.UserRepo
{
    public interface IUserRepository
    {
        User CreateUser(string userId);
        void DeleteUser(string userId);
    }
}
