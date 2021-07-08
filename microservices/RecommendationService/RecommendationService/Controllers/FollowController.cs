using Microsoft.AspNetCore.Mvc;
using RecommendationService.Service.FollowServices;

namespace RecommendationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FollowController : ControllerBase
    {
        private readonly IFollowService _followService;

        public FollowController(IFollowService followService)
        {
            _followService = followService;
        }

        [HttpPost]
        [Route("{sourceUserId}/{destinationUserId}")]
        public IActionResult CreateFollow(string sourceUserId, string destinationUserId)
        {
            _followService.Follow(sourceUserId, destinationUserId);
            return Ok();
        }

        [HttpDelete]
        [Route("{sourceUserId}/{destinationUserId}")]
        public IActionResult DeleteFollow(string sourceUserId, string destinationUserId)
        {
            _followService.UnFollow(sourceUserId, destinationUserId);
            return Ok();
        }
    }
}
