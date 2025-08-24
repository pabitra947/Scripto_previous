
import React from "react";
const Navbar = () => {
  return (

    <nav className="fixed flex items-center justify-between px-8 py-4 bg-white shadow-[0_4px_10px_rgba(156,163,175,0.15)] top-0 left-0 w-full z-50">
      {/* Logo */}
      <div className="flex items-center space-x-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
          className="h-7 w-7 text-black"
          aria-hidden="true"
        >
          <path d="M12 7v14"></path>
          <path d="M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z"></path>
        </svg>
        <h2 className="text-primary font-bold text-xl tracking-tight">Scripto</h2>
      </div>

      
      <div className="hidden md:block">
            <div className="ml-10 flex items-baseline space-x-4">
              <a href="#hero" className="text-foreground hover:text-primary hover:font-bold px-3 py-2 transition-colors">
                Home
              </a>
              <a href="#features" className="text-foreground hover:text-primary hover:font-bold px-3 py-2 transition-colors">
                Features
              </a>
              <a href="#about" className="text-foreground hover:text-primary hover:font-bold px-3 py-2 transition-colors">
                About
              </a>
              <a href="#contact" className="text-foreground hover:text-primary hover:font-bold px-3 py-2 transition-colors">
                Contact
              </a>
            </div>
          </div>

    
      <button className="bg-black text-white px-5 py-2 rounded-lg font-medium hover:bg-gray-900">
        Get Started
      </button>
    </nav>

  );
};

export default Navbar;