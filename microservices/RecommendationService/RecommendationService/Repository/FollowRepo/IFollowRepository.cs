using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RecommendationService.Repository.FollowRepo
{
    public interface IFollowRepository
    {
        void Follow(string sourceUserId, string destinationUserId);
        void UnFollow(string sourceUserId, string destinationUserId);
        void Block(string sourceUserId, string destinationUserId);
        void UnBlock(string sourceUserId, string destinationUserId);
    }
}
