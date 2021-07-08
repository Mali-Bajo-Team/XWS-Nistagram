using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RecommendationService.Service.FollowServices
{
    public interface IFollowService
    {
        void Follow(string sourceUserId, string destinationUserId);
    }
}
