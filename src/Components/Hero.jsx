import React from "react";
import heroImg from "../assets/book_img.jpg";
import { FaArrowRight } from "react-icons/fa6";

const Hero = () => {
  return (
    <section
  id="hero"
  className="flex flex-col md:flex-row items-center justify-between px-12 py-16 bg-white font-sans"
>
   <div className="flex flex-col md:flex-row items-center justify-between max-w-7xl mx-auto px-6 md:px-12 py-32">
        
      {/* Left Content */}
      <div className="max-w-xl md:w-1/2">
        <h1 className="hero-font text-5xl font-bold leading-tight text-gray-900">
          Build Amazing <br /> Web Experiences
        </h1>
        <p className="hero-font mt-6 text-lg text-gray-500">
          Create stunning, responsive websites that captivate your audience and
          drive results. Our modern approach combines beautiful design with
          powerful functionality.
        </p>

        <div className="mt-8 flex flex-wrap gap-4">
          <button className="bg-black text-white px-6 py-3 rounded-lg font-medium hover:shadow-lg hover:shadow-gray-700 transform hover:scale-95 cursor-pointer transition-colors duration-200 inline-flex items-center">
            Get Started
            <FaArrowRight className="ml-2 pt-0.5 h-4 w-4" />
          </button>
          <button className="border border-gray-300 px-6 py-3 rounded-lg font-medium flex items-center space-x-2 hover:bg-gray-50">
            <span>▶</span>
            <span>Watch Demo</span>
          </button>
        </div>
      </div>

      {/* Right Image */}
      <div className="mt-12 relative sm:max-w-lg sm:mx-auto lg:mt-0 lg:max-w-none lg:mx-0 lg:col-span-6 lg:flex lg:items-center">
      <div className="relative mx-auto w-full rounded-lg shadow-lg lg:max-w-md">
        <img
          src={heroImg}
          alt="Scirpto hero"
          className="rounded-xl w-full"
        />
      </div>
      </div>
    </div>
</section>

  );
};

export default Hero;

