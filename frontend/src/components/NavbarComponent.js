import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const navbarStyle = {
  backgroundColor: "#52abff", // Light blue color
  fontSize: "20px", // Larger font size
};

const linkStyle = {
  color: "#fff", // White color for the text
  fontWeight: "bold", // Bold font weight
};

function NavbarComponent() {
  return (
    <Navbar expand="lg" style={navbarStyle}>
      <Container>
        <Navbar.Brand as={Link} to="/" style={linkStyle}>
          Car-Store
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/" style={linkStyle}>
              Manufacturers
            </Nav.Link>
            <Nav.Link as={Link} to="/orders" style={linkStyle}>
              Orders
            </Nav.Link>
            <Nav.Link as={Link} to="/customers" style={linkStyle}>
              Customer
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavbarComponent;
