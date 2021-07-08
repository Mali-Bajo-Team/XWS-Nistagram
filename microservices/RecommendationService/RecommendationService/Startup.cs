using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.OpenApi.Models;
using Neo4jClient;
using RecommendationService.Repository.FollowRepo;
using RecommendationService.Repository.RecommendationRepo;
using RecommendationService.Repository.UserRepo;
using RecommendationService.Service.FollowServices;
using RecommendationService.Service.RecommendationServices;
using RecommendationService.Service.UserServices;

namespace RecommendationService
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            // Configure Graph DB [Neo4j]
            // var graphDatabaseURI = "https://bolt://localhost:7687"; // TODO: Change this to the docker paths !!!
            var graphDatabaseURI = "http://neo4j:7474"; // TODO: Change this to the docker paths !!!
            // var graphDB = new GraphClient(new Uri(graphDatabaseURI), "neo4j", "secret");
            var graphDB = new GraphClient(new Uri(graphDatabaseURI));

            var task = graphDB.ConnectAsync();
            

            services.AddScoped<IUserService, UserService>();
            services.AddScoped<IUserRepository, UserRepository>();

            services.AddScoped<IFollowService, FollowService>();
            services.AddScoped<IFollowRepository, FollowRepository>();

            services.AddScoped<IRecommendationService,Service.RecommendationServices.RecommendationService>();
            services.AddScoped<IRecommendationRepository, RecommendationRepository>();

            services.AddControllers();


            services.AddSingleton<IGraphClient>(graphDB);

            services.AddCors(o => o.AddPolicy("CorsPolicy", builder =>
            {
                builder
                    .AllowAnyOrigin()
                    .AllowAnyMethod()
                    .AllowAnyHeader();
            }));

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("XWS", new OpenApiInfo { Title = "RecommendationService", Version = "v1" });
            }); task.Wait();
            services.AddApplicationInsightsTelemetry();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/XWS/swagger.json", "RecommendationService XWS"));
            }

            //app.UseHttpsRedirection();

            app.UseRouting();
            app.UseCors("CorsPolicy");

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
