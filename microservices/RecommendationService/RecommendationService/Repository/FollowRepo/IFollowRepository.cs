using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RecommendationService.Repository.FollowRepo
{
    public interface IFollowRepository
    {
        void Follow(string sourceUserId, string destinationUserId);
    }
}
