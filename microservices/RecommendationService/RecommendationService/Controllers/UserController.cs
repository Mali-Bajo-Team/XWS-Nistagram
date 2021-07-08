using Microsoft.AspNetCore.Mvc;
using RecommendationService.Model;
using RecommendationService.Service.UserServices;

namespace RecommendationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpPost]
        [Route("{id}")]
        public IActionResult CreateNewUser(string id)
        {
            return Ok(_userService.CreateUser(id));
        }

        [HttpGet("{id}")]
        public IActionResult GetUser(string id)
        {
            User user = new User {Id = id};
            return Ok(user);
        }
    }
}


