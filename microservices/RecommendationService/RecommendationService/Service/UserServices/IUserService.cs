using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using RecommendationService.Model;

namespace RecommendationService.Service.UserServices
{
    public interface IUserService
    {
        User CreateUser(string userId);
    }
}
