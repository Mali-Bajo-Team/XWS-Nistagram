using Microsoft.AspNetCore.Mvc;
using RecommendationService.Model;
using RecommendationService.Service.UserServices;

namespace RecommendationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RecommendationController : ControllerBase
    {
        private readonly IUserService _userService;

        public RecommendationController(IUserService userService)
        {
            _userService = userService;
        }



        [HttpGet("{id}")]
        public IActionResult GetUser(string id)
        {
            User user = new User {Id = id};
            return Ok(user);
        }
    }
}


