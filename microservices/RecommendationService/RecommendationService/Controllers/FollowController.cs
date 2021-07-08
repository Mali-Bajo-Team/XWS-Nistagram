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
        [Route("follow/{sourceUserId}/{destinationUserId}")]
        public IActionResult CreateFollow(string sourceUserId, string destinationUserId)
        {
            _followService.Follow(sourceUserId, destinationUserId);
            return Ok();
        }

        [HttpDelete]
        [Route("follow/{sourceUserId}/{destinationUserId}")]
        public IActionResult DeleteFollow(string sourceUserId, string destinationUserId)
        {
            _followService.UnFollow(sourceUserId, destinationUserId);
            return Ok();
        }

        [HttpPost]
        [Route("block/{sourceUserId}/{destinationUserId}")]
        public IActionResult BlockUser(string sourceUserId, string destinationUserId)
        {
            _followService.Block(sourceUserId, destinationUserId);
            return Ok();
        }

        [HttpDelete]
        [Route("block/{sourceUserId}/{destinationUserId}")]
        public IActionResult UnBlockUser(string sourceUserId, string destinationUserId)
        {
            _followService.UnBlock(sourceUserId, destinationUserId);
            return Ok();
        }
    }
}
