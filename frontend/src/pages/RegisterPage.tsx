import { useState } from 'react';
import { Container, Paper, Title, TextInput, PasswordInput, Button, Text, Anchor, Group } from '@mantine/core';
import { useForm } from '@mantine/form';
import { useNavigate, Link } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { authService } from '../services';

export function RegisterPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const form = useForm({
    initialValues: {
      email: '',
      password: '',
      confirmPassword: '',
      firstName: '',
      lastName: '',
    },
    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
      password: (value) => (value.length >= 6 ? null : 'Password must be at least 6 characters'),
      confirmPassword: (value, values) =>
        value === values.password ? null : 'Passwords do not match',
      firstName: (value) => (value.trim() ? null : 'First name is required'),
      lastName: (value) => (value.trim() ? null : 'Last name is required'),
    },
  });

  const handleSubmit = async (values: typeof form.values) => {
    setLoading(true);
    try {
      const response = await authService.register({
        email: values.email,
        password: values.password,
        firstName: values.firstName,
        lastName: values.lastName,
      });
      
      localStorage.setItem('accessToken', response.accessToken);
      localStorage.setItem('refreshToken', response.refreshToken);
      
      notifications.show({
        title: 'Success',
        message: 'Account created successfully!',
        color: 'green',
      });
      
      navigate('/catalog');
    } catch (error: any) {
      notifications.show({
        title: 'Error',
        message: error.response?.data?.message || 'Registration failed',
        color: 'red',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container size={420} my={40}>
      <Title ta="center" mb="md">
        Create an account
      </Title>

      <Paper withBorder shadow="md" p={30} radius="md">
        <form onSubmit={form.onSubmit(handleSubmit)}>
          <Group grow>
            <TextInput
              label="First Name"
              placeholder="John"
              {...form.getInputProps('firstName')}
            />
            <TextInput
              label="Last Name"
              placeholder="Doe"
              {...form.getInputProps('lastName')}
            />
          </Group>

          <TextInput
            label="Email"
            placeholder="your@email.com"
            mt="md"
            {...form.getInputProps('email')}
          />

          <PasswordInput
            label="Password"
            placeholder="Your password"
            mt="md"
            {...form.getInputProps('password')}
          />

          <PasswordInput
            label="Confirm Password"
            placeholder="Confirm your password"
            mt="md"
            {...form.getInputProps('confirmPassword')}
          />

          <Button fullWidth mt="xl" type="submit" loading={loading}>
            Create account
          </Button>
        </form>

        <Group justify="center" mt="md">
          <Anchor component={Link} to="/login" size="sm">
            Already have an account? Sign in
          </Anchor>
        </Group>
      </Paper>
    </Container>
  );
}
